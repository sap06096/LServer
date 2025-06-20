package com.example.LServer.model.Products;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category", schema = "LCore")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ApiModel(description = "카테고리 정보")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // ← 필드에 붙임
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "level")
    private int level;

    @Column(name = "sort_order")
    private Integer sortOrder;
}

