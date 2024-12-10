package com.example.LServer.repository.Users;

import com.example.LServer.model.Users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findUsersByLoginId(String loginId);
    UsersEntity findUsersByPhoneNumber(String phoneNumber);
}
