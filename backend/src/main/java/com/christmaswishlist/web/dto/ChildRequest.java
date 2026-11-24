package com.christmaswishlist.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChildRequest(@NotBlank @Size(max = 100) String firstName, @NotNull @Min(0) Integer age) { }
