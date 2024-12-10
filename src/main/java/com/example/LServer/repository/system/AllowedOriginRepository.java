package com.example.LServer.repository.system;

import com.example.LServer.model.setting.AllowedOriginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllowedOriginRepository extends JpaRepository<AllowedOriginEntity, Long> {
    List<AllowedOriginEntity> findAllByUse(Boolean use);
}