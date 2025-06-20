package com.example.LServer.repository.Products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LServer.model.Products.CategoryEntity;

public interface ProductsRepository extends JpaRepository<CategoryEntity, Long>{

    List<CategoryEntity> findAll();

    List<CategoryEntity> findByLevel(int level);

    List<CategoryEntity> findByParentIdAndLevel(int id, int level);
}
