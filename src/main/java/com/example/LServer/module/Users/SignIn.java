package com.example.LServer.module.Users;

import com.example.LServer.model.Users.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class SignIn {

    private final Users usersModule;

    public String createAccessToken(UsersEntity usersEntity) throws Exception{

        String token = null;

        try {
             token = usersEntity.getUserToken();
             if(ObjectUtils.isEmpty(token) || !StringUtils.hasLength(token) && !StringUtils.hasLength(usersEntity.getPhoneNumber())){
                 token = usersModule.createUserToken(usersEntity.getPhoneNumber());
             }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return token;
    }
}
