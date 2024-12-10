package com.example.LServer.module.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResultUtil<T> {

    public ResponseEntity fail(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", -1);
        result.put("desc", message);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity fail2(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", -2);
        result.put("desc", message);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity success(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        result.put("desc", message);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> successData(T data, String dataKey, String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        result.put(dataKey, data);
        result.put("desc", message);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> error(HttpStatus status, String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", -2);
        result.put("desc", message);
        return ResponseEntity.status(status).body(result);
    }

    public Map<String, Object> failMap(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        result.put("desc", message);
        return result;
    }

    public Map<String, Object> failResultMap(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", -1);
        result.put("desc", message);
        return result;
    }

    public Map<String, Object> failResult2Map(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", -2);
        result.put("desc", message);
        return result;
    }

    public Map<String, Object> successMap() {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        return result;
    }

    public Map<String, Object> successResultMap(String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        result.put("desc", message);
        return result;
    }

    public Map<String, Object> successDataMap(T data, String dataKey, String message) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        result.put(dataKey, data);
        result.put("desc", message);
        return result;
    }

    public Map<String, Object> successDataNoDescMap(T data, String dataKey) {
        Map<String, Object> result = Generics.newHashMap();
        result.put("result", 0);
        result.put(dataKey, data);
        return result;
    }

    public boolean userCheck(String userToken) {
        if (!StringUtils.hasLength(userToken))
            return false;
        return true;
    }
}
