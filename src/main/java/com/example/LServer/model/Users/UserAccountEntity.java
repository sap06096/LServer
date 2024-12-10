package com.example.LServer.model.Users;

import com.example.LServer.constant.Users.UserAccountType;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

@ApiModel(description = "사용자 계정 정보")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usersAccount", schema = "LCore")
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "login_pwd")
    private String loginPwd;

    @Enumerated(EnumType.STRING) // Enum 타입일 경우
    @Column(name = "type")
    private UserAccountType type;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "agency_id")
    private Long agencyId;
}
