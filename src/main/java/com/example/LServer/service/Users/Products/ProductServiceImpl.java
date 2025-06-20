package com.example.LServer.service.Users.Products;

import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.example.LServer.item.Products.CategoryDto;
import com.example.LServer.model.Products.CategoryEntity;
import com.example.LServer.repository.Products.ProductsRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    
    private final ProductsRepository productsRepository;

    @Override
    public List<CategoryDto> getCategoryList(int id, int level) throws Exception {

        List<CategoryEntity> entityList = null;

        switch (level) {
            case 1:
            entityList = productsRepository.findByLevel(level);
                break;
            case 2:
            case 3:
            entityList = productsRepository.findByParentIdAndLevel(id, level);
                break;
            default:
                break;
        }
    
        if(entityList == null && entityList.size() <= 0) {
            throw new Exception("카테고리 리스트가 없습니다.");
        }

        return CategoryDto.builder().build().toDtoList(entityList);
    }
}