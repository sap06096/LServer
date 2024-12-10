package com.example.LServer.controller;

import com.example.LServer.item.Users.UsersDto;
import com.example.LServer.module.common.Generics;
import com.example.LServer.service.Users.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(){
        Map<String, Object> result = Generics.newHashMap();

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/checkDuplicateId")
    public ResponseEntity checkDuplicateId(@RequestParam("loginId") String loginId) {
        Map<String, Object> result = Generics.newHashMap();

        try{
            boolean isUser = userService.getUserByLoginId(loginId);
            if(isUser){
                result.put("result", -1);
                result.put("desc", "해당 아이디는 중복된 아이디입니다.");
            }else{
                result.put("result", 0);
                result.put("desc", "허용가능한 아이디입니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/user")
    @ApiOperation(value = "사용자 생성", notes = "ReqeustBody의 정보로 사용자를 생성합니다.")
    public ResponseEntity createUser(@RequestBody @Valid UsersDto dto) {
        return ResponseEntity.ok().body(userService.createUser(dto));
    }
}
