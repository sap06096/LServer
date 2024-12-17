package com.example.LServer.model.Users;

import com.example.LServer.constant.Users.SnsKind;
import com.example.LServer.constant.Users.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(description = "사용자 정보")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "UsersEntity")
@Table(name = "users", schema = "LCore")
public class UsersEntity {
    private Long id;
    private String userToken;
    private String loginId;
    private String loginPwd;
    private String name;
    private Integer age;
    private Integer gender;
    private String email;
    private UserStatus status;
    private String phoneNumber;
    private String snsToken;
    private SnsKind snsKind;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime regDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime expireDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "user_token")
    public String getUserToken() {
        return userToken;
    }

    @Column(name = "login_id", length = 45)
    public String getLoginId() {
        return loginId;
    }

    @Column(name = "login_pwd", length = 128)
    public String getLoginPwd() {
        return loginPwd;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        return name;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    @Column(name = "gender", nullable = false)
    public Integer getGender() {
        return gender;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(name = "status", nullable = false)
    public UserStatus  getStatus() {
        return status;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "reg_date")
    public LocalDateTime getRegDate() {
        return regDate;
    }

    @Column(name = "expire_date")
    public LocalDateTime getExpireDate() { return expireDate; }

    @Column(name = "sns_token")
    public String getSnsToken() {
        return snsToken;
    }

    @Column(name = "sns_kind")
    public SnsKind getSnsKind() {
        return snsKind;
    }

}
