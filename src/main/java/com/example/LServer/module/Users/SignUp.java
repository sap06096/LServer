package com.example.LServer.module.Users;

import ch.qos.logback.core.util.StringUtil;
import com.example.LServer.constant.Users.UserStatus;
import com.example.LServer.item.Users.UsersDto;
import com.example.LServer.model.Users.UsersEntity;
import com.example.LServer.repository.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SignUp {

    private final UsersRepository usersRepository;
    private final UsersFind usersFindModule;
    private final Users usersModule;

    public UsersEntity idAndPwdCreateUser(UsersDto usersDto) throws Exception{
        if(ObjectUtils.isEmpty(usersDto.getLoginId()) || ObjectUtils.isEmpty(usersDto.getLoginPwd())){
            throw new Exception("아이디와 패스워드는 필수입니다.");
        }
        UsersEntity usersEntity = basicSettingUsersEntity(usersDto);
        usersEntity.setLoginId(usersDto.getLoginId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        usersEntity.setLoginPwd(bCryptPasswordEncoder.encode(usersDto.getLoginPwd()));

        return usersRepository.save(usersEntity);
    }

    public UsersEntity basicSettingUsersEntity(UsersDto usersDto) throws Exception {
        UsersEntity usersEntity = usersFindModule.getUserByEncodePhoneNumber(usersDto.getEncodePhoneNumber());
        if(ObjectUtils.isEmpty(usersEntity)){
            usersEntity = UsersEntity.builder()
                    .regDate(LocalDateTime.now())
                    .age(usersDto.getAge())
                    .name(usersDto.getName())
                    .email(usersDto.getEmail())
                    .gender(usersDto.getGender())
                    .phoneNumber(usersDto.getEncodePhoneNumber())
                    .userToken(usersModule.createUserToken(usersDto.getPhoneNumber()))
                    .status(UserStatus.USER_STATUS_ACTIVE)
                    .build();
            if(StringUtils.hasLength(usersDto.getPhoneNumber()) && StringUtils.hasLength(usersDto.getEncodePhoneNumber())){
                usersEntity.setPhoneNumber(usersDto.getEncodePhoneNumber());
            }
        }

        return usersEntity;
    }
}
