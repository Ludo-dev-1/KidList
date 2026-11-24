package com.christmaswishlist.web.mapper;

import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.model.Gift;
import com.christmaswishlist.web.dto.ChildResponse;
import com.christmaswishlist.web.dto.GiftResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiMapper {
    ChildResponse toResponse(Child child);
    GiftResponse toResponse(Gift gift);
}
