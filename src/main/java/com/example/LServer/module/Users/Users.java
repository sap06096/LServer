package com.example.LServer.module.Users;

import ch.qos.logback.core.util.StringUtil;
import com.example.LServer.constant.Users.UserStatus;
import com.example.LServer.item.Users.UsersDto;
import com.example.LServer.model.Users.UsersEntity;
import com.example.LServer.module.Security.AES256Cipher;
import com.example.LServer.repository.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class Users {

    private static final String USER_TOKEN_PREFIX = "LCORE_USER";
    private final UsersRepository usersRepository;
    private final AES256Cipher aes256Cipher;

    public boolean enableUser(UsersEntity usersEntity) {
        if (usersEntity.getStatus() == UserStatus.USER_STATUS_EXPIRE_RESERVED && !ObjectUtils.isEmpty(usersEntity.getExpireDate())) {
            // 3개월이 지나지 않았다면...
            LocalDateTime threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);
            // 만료 날짜가 3개월 전 이후인 경우 3개월 미만임을 의미합니다
            if (usersEntity.getExpireDate().isAfter(threeMonthsAgo)) {
                return false;
            }
        }
        return true;
    }

    public String createUserToken(String phoneNumber) throws Exception{
        if(!StringUtils.hasLength(phoneNumber)) {
            throw new Exception("휴대폰 번호를 전달 받지 못했습니다.");
        }
        try{
            return aes256Cipher.AES_Encode(USER_TOKEN_PREFIX + "|" + phoneNumber);
        }catch (Exception e){
            throw new Exception("userToken 갱신 실패했습니다. \n문의 부탁드립니다.");
        }
    }

    /**
     * phoneNum 문자열에 "탈퇴_"가 포함되어 있는지를 검사하고, 포함되어 있지 않으면 true, 포함되어 있으면 false를 반환
     * @param phoneNum
     * @return
     */
    public boolean expPhoneNum(String phoneNum) {
        if (ObjectUtils.isEmpty(phoneNum)) { return false; }

        return !phoneNum.contains("탈퇴");
    }
}
