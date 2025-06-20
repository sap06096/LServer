package com.example.LServer.service.Users.Products;

import java.util.List;

import com.example.LServer.item.Products.CategoryDto;

public interface ProductService {

    List<CategoryDto> getCategoryList(int id, int level) throws Exception;    
}
