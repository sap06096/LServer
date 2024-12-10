package com.example.LServer.item.Users;

import com.example.LServer.constant.Users.SnsKind;
import com.example.LServer.constant.Users.UserStatus;
import com.example.LServer.item.EntityMapper;
import com.example.LServer.model.Users.UsersEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UsersDto implements Serializable, EntityMapper<UsersDto, UsersEntity> {
    private Long id;
    @NotEmpty(message = "유저 토큰을 전달 받지 못했습니다.")
    private String userToken;
    @ApiParam(value = "사용자 로그인 아이디", required = false, example = "사용자 지정")
    private String loginId;
    @ApiParam(value = "사용자 로그인 패스워드", required = false, example = "사용자 지정")
    private String loginPwd;
    @ApiParam(value = "사용자 이름", required = false, example = "홍길동")
    private String name;
    private Integer age;
    private Integer gender;
    private String email;
    private UserStatus status;
    private String phoneNumber;
    private String decodePhoneNumber;
    private String snsToken;
    private SnsKind snsKind;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime expireDate;

    //Trasient
    private String encodePhoneNumber;
    private String jwtAccessToken;

    @Override
    public UsersDto toDto(UsersEntity entity) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(entity, UsersDto.class);
    }

    @Override
    public UsersEntity toEntity(UsersDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(dto, UsersEntity.class);
    }

    @Override
    public List<UsersEntity> toEntityList(List<UsersDto> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<UsersDto> toDtoList(List<UsersEntity> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
