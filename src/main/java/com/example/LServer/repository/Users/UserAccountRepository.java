package com.example.LServer.repository.Users;


import com.example.LServer.constant.Users.UserAccountType;
import com.example.LServer.model.Users.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    UserAccountEntity findUserAccountEntityByLoginIdAndType(String id, UserAccountType type);
}
