package com.example.LServer.service.Users;

import com.example.LServer.item.Users.UsersDto;

import java.util.Map;

public interface UserService {

    boolean getUserByLoginId(String loginId);

    Map<String, Object> createUser(UsersDto dto) throws Exception;

    UsersDto getUserByLoginIdAndPwd(UsersDto dto) throws Exception;

    UsersDto getUserByUserToken(String userToken) throws Exception;
}
