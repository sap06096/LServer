package com.example.LServer.item.Products;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.example.LServer.item.EntityMapper;
import com.example.LServer.model.Products.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable, EntityMapper<CategoryDto, CategoryEntity> {
    private int id;
    private String name;
    private Integer parentId;
    private int level;
    private Integer sortOrder;

    @Override
    public CategoryDto toDto(CategoryEntity entity) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(entity, CategoryDto.class);
    }

    @Override
    public CategoryEntity toEntity(CategoryDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(dto, CategoryEntity.class);
    }

    @Override
    public List<CategoryEntity> toEntityList(List<CategoryDto> dtoList){
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> toDtoList(List<CategoryEntity> entityList){
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    } 
}