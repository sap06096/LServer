package com.example.LServer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.LServer.item.Products.CategoryDto;
import com.example.LServer.module.common.Generics;
import com.example.LServer.service.Users.Products.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {
    
    private final ProductService productService;

    @GetMapping("/category")
    public ResponseEntity searchCategoryList(@RequestParam("id") int id,
                                            @RequestParam("level") int level) {

        Map<String, Object> result = Generics.newHashMap();
        try{
            List<CategoryDto> categoryList = productService.getCategoryList(id, level);
            result.put("data", categoryList);
            result.put("desc", "성공적으로 조회하였습니다.");
            result.put("result", 0);
        }catch(Exception e) {
            result.put("result", -1); 
            result.put("desc", e.getMessage());
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }
}