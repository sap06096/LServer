package com.example.LServer.service.Users;

import com.example.LServer.constant.Users.UserAccountType;
import com.example.LServer.constant.Users.UserStatus;
import com.example.LServer.item.Users.UsersDto;
import com.example.LServer.model.Users.UserAccountEntity;
import com.example.LServer.model.Users.UsersEntity;
import com.example.LServer.module.Security.AES256Cipher;
import com.example.LServer.module.Users.*;
import com.example.LServer.module.common.Generics;
import com.example.LServer.module.common.PhoneNumberUtil;
import com.example.LServer.module.common.ResultUtil;
import com.example.LServer.repository.Users.UserAccountRepository;
import com.example.LServer.repository.Users.UsersRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.description.type.TypeDescription.Generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UsersRepository usersRepository;
    private final UsersFind usersFindModule;
    private final Users usersModule;
    private final UsersAccount usersAccountModule;
    private final PhoneNumberUtil phoneNumberUtil;
    private final AES256Cipher aes256Cipher;
    private final ResultUtil resultUtil;
    private final SignUp signUpModule;
    private final SignIn signInModule;
    private final UserAccountRepository usersAccountRepository;

    @Override
    public boolean getUserByLoginId(String loginId){
        UsersEntity usersEntity = usersRepository.findUsersByLoginId(loginId);

        boolean isUser = false;
        if(!ObjectUtils.isEmpty(usersEntity)){
            isUser = true;
        }
        return isUser;
    }

    @Override
    @Transactional
    public Map<String, Object> createUser(UsersDto dto) throws Exception{
        Map<String, Object> result = new HashMap<>();

        UsersEntity usersEntity = null;
        boolean isUser = false;

        // security 암호화 폰번호 : 조회 하는 용도로만 쓰임
        if (!ObjectUtils.isEmpty(dto.getPhoneNumber())) {
            // Fixme. base64 암호화된 휴대폰 번호인지 검증 : 클라이언트에서 폰번호를 받는경우 무조건 base64 암호화로 받는다.
            if (phoneNumberUtil.isValidBase64Phone(dto.getPhoneNumber())) {
                try {
                    Base64.Decoder decoder = Base64.getDecoder();
                    String decodeBase64PhoneNumber = new String(Base64.getDecoder().decode(dto.getPhoneNumber()), "UTF-8");
                    dto.setEncodePhoneNumber(aes256Cipher.AES_Encode(decodeBase64PhoneNumber));
                } catch (UnsupportedEncodingException e) {
                    resultUtil.failResultMap(e.getMessage());
                }
            }
        }

        usersEntity = usersFindModule.getUserByLoginId(dto.getLoginId());
        //활성 회원 검증
        if(!ObjectUtils.isEmpty(usersEntity)){
            String desc = enableUsersCheck(usersEntity);
            if(StringUtils.hasLength(desc)){
                throw new Exception(desc);
            }
        }

        try {
            usersAccountModule.isValidID(dto.getLoginId(), UserAccountType.USER_ACCOUNT_TYPE_USERS);
            usersAccountModule.isValidPassword(dto.getLoginPwd());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        usersEntity = signUpModule.idAndPwdCreateUser(dto);

        UserAccountEntity usersAccount = usersAccountRepository.findUserAccountEntityByLoginIdAndType(usersEntity.getLoginId(), UserAccountType.USER_ACCOUNT_TYPE_USERS);
        if(ObjectUtils.isEmpty(usersAccount)){
            try{
                usersAccount = usersAccountRepository.save(UserAccountEntity.builder()
                        .usersId(usersEntity.getId())
                        .loginId(usersEntity.getLoginId())
                        .loginPwd(usersEntity.getLoginPwd())
                        .type(UserAccountType.USER_ACCOUNT_TYPE_USERS)
                        .build());
            }catch (Exception e){
                e.printStackTrace();
                throw new Exception("회원 계쩡 새성 중 오류 발생" + e.getMessage());
            }

            if(ObjectUtils.isEmpty(usersAccount)){
                throw new Exception("회원 로그인 계정이 생성되지 않았습니다.");
            }
        }

        if(ObjectUtils.isEmpty(usersEntity)){
            logger.info("회원가입 실패");
            logger.info("dto 정보 : " + dto);
            return resultUtil.failResultMap("회원가입을 실패하였습니다. \n문의 부탁드립니다.");
        }

        UsersDto usersDto = UsersDto.builder().build().toDto(usersEntity);
        if(!ObjectUtils.isEmpty(usersEntity.getPhoneNumber()) && usersModule.expPhoneNum(usersEntity.getPhoneNumber())){
            usersDto.setDecodePhoneNumber(aes256Cipher.AES_Decode(usersEntity.getPhoneNumber()));
        }

        usersDto.setJwtAccessToken(signInModule.createAccessToken(usersEntity));

        result.put("result", 0);
        result.put("desc", "회원가입이 성공적으로 처리되었습니다.");
        result.put("usersDto", usersDto);

        return result;
    }

    protected String enableUsersCheck(UsersEntity entity){
        String desc = null;
        if(entity.getStatus() != UserStatus.USER_STATUS_ACTIVE){
            switch (entity.getStatus()){
                case USER_STATUS_EXPIRE_RESERVED:
                    // 회원 재가입 확인
                    if (!usersModule.enableUser(entity)) {
                        desc = "탈퇴 회원으로 90일간 가입이 불가능합니다.";
                    }
                    break;
                case USER_STATUS_EXPIRED:
                    desc = "완전 탈퇴 회원";
                    break;
                case USER_STATUS_PAUSE:
                case USER_STATUS_WAIT:
                    desc = "일시 정지 상태입니다. 고객센터로 문의 하세요.";
                    break;
                case USER_STATUS_UNFIXED:
                    desc = "월정액 미납 회원입니다. 고객센터로 문의 하세요";
                    break;
                case USER_STATUS_NONE:
                    desc = "미처리 상태입니다.";
                    break;
            }
        }else{
            return null;
        }
        return desc;
    }

    @Override
    public UsersDto getUserByLoginIdAndPwd(UsersDto dto) throws Exception{

        if(ObjectUtils.isEmpty(dto.getLoginId()) || ObjectUtils.isEmpty(dto.getLoginPwd())){
            throw new Exception("아이디 혹은 비밀번호가 없습니다.");
        }

        UsersEntity entity = usersFindModule.getUserByLoginId(dto.getLoginId());
        boolean isUser = false;

        if(entity != null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(!bCryptPasswordEncoder.matches(dto.getLoginPwd(), entity.getLoginPwd())){
                throw new Exception("비밀번호가 올바르지 않습니다.");
            }
        }else{
            throw new Exception("유저 정보가 존재하지 않습니다.");
        }

        return UsersDto.builder().build().toDto(entity);
    }

    public UsersDto getUserByUserToken(String userToken) throws Exception {

        UsersEntity usersEntity = usersFindModule.getUserByUserToken(userToken);
        if(usersEntity == null){
            throw new Exception("유저 정보가 존재하지 않습니다. 로그인을 시도해주세요.");
        }

        return UsersDto.builder().build().toDto(usersEntity);
    }

}
