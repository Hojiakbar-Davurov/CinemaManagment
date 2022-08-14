package com.example.cinemamanagment.mapper;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {

    @Named(value = "toDto")
    D toDto(E entity);

    @Named(value = "toEntity")
    E toEntity(D dto);

    @IterableMapping(qualifiedByName = "toDto")
    List<D> toDto(List<E> entityList);

    @IterableMapping(qualifiedByName = "toEntity")
    List<E> toEntity(List<D> dtoList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    D toDtoId(E entity);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @IterableMapping(qualifiedByName = "id")
    Set<D> toDtoIdSet(Set<E> entity);


}
