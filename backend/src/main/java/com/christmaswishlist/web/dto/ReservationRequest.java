package com.christmaswishlist.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReservationRequest(@NotBlank @Size(max = 255) String reservedBy) { }
