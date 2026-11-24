package com.christmaswishlist.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GiftRequest(@NotBlank @Size(max = 255) String title,
                          String description, String imageUrl, String productUrl) { }
