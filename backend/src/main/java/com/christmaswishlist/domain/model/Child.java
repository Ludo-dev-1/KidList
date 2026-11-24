package com.christmaswishlist.domain.model;

public record Child(Long id, String firstName, int age) {
    public Child {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("Le prénom est obligatoire.");
        firstName = firstName.trim();
        if (firstName.length() > 100) throw new IllegalArgumentException("Le prénom ne peut pas dépasser 100 caractères.");
        if (age < 0) throw new IllegalArgumentException("L'âge ne peut pas être négatif.");
    }
}
