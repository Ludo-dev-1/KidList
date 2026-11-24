package com.christmaswishlist.web.dto;

import java.time.LocalDateTime;

public record GiftResponse(Long id, String title, String description, String imageUrl, String productUrl,
                           String reservedBy, LocalDateTime reservedAt, Long childId) { }
