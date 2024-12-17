package com.example.LServer.controller;

import com.example.LServer.item.Users.UsersDto;
import com.example.LServer.module.common.Generics;
import com.example.LServer.service.Users.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

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
    public ResponseEntity createUser(@RequestBody @Valid UsersDto dto) throws Exception {
        Map<String,Object> result = Generics.newHashMap();
        try{
            result = userService.createUser(dto);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    @ApiOperation(value = "id,pwd 로그인")
    public ResponseEntity loginUser(@RequestBody @Valid UsersDto dto) throws Exception{

        Map<String, Object> result = Generics.newHashMap();

        try{
            UsersDto usersDto = userService.getUserByLoginIdAndPwd(dto);
            
            if(ObjectUtils.isEmpty(usersDto)){
                result.put("result", -1);
                result.put("desc", "로그인을 실패하였습니다.");
            }

            result.put("data", usersDto);

        }catch(Exception e){
            result.put("result", -1);
            result.put("desc", e.getMessage());
            return ResponseEntity.ok().body(result);
        }

        result.put("result", 0);
        result.put("desc", "성공적으로 가져왔습니다.");        

        return ResponseEntity.ok().body(result);
    }
    
    @GetMapping("/user/userToken")
    @ApiOperation(value = "US_TK로 유저정보 추출")
    public ResponseEntity getUserByUserToken(@RequestHeader("Authorization") String authToken){
        Map<String, Object> result = Generics.newHashMap();

        String userToken = authToken.replace("Bearer ", "");

        if(!StringUtils.hasLength(userToken)){
            result.put("result", -1);
            result.put("desc", "accessToken is null");
            return new ResponseEntity(result, HttpStatus.OK);
        }

        try{
            UsersDto usersDto = userService.getUserByUserToken(userToken);
            if(!ObjectUtils.isEmpty(usersDto)){
                result.put("result", 0);
                result.put("desc", "성공적으로 조회하였습니다.");
                result.put("data", usersDto);
            }
        }catch(Exception e){
            result.put("result", -1);
            result.put("desc", e.getMessage());
        }

        return new ResponseEntity(result, HttpStatus.OK);
    } 

}
