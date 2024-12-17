package com.example.LServer.module.Users;

import com.example.LServer.model.Users.UsersEntity;
import com.example.LServer.repository.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersFind {

    private final UsersRepository usersRepository;

    public UsersEntity getUserByLoginId(String loginId){
        return usersRepository.findUsersByLoginId(loginId);
    }

    public UsersEntity getUserByEncodePhoneNumber(String phoneNumber){
        return usersRepository.findUsersByPhoneNumber(phoneNumber);
    }

    public UsersEntity getUserByUserToken(String userToken){
        return usersRepository.findUsersByUserToken(userToken);
    }
}
