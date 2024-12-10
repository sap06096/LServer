package com.example.LServer.item;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(final D dto);

    D toDto(final E entity);

    List<E> toEntityList(final List<D> dtoList);

    List<D> toDtoList(final List<E> entityList);
}

