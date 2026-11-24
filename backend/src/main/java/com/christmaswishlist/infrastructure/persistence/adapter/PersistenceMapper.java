package com.christmaswishlist.infrastructure.persistence.adapter;

import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.model.Gift;
import com.christmaswishlist.infrastructure.persistence.entity.ChildEntity;
import com.christmaswishlist.infrastructure.persistence.entity.GiftEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {
    Child toDomain(ChildEntity entity);
    ChildEntity toEntity(Child child);
    Gift toDomain(GiftEntity entity);
    GiftEntity toEntity(Gift gift);
}
