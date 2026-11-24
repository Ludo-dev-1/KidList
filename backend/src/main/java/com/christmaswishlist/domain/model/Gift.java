package com.christmaswishlist.domain.model;

import java.time.LocalDateTime;

public record Gift(Long id, String title, String description, String imageUrl, String productUrl,
                   String reservedBy, LocalDateTime reservedAt, Long childId) {
    public Gift {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Le titre du cadeau est obligatoire.");
        if (title.length() > 255) throw new IllegalArgumentException("Le titre ne peut pas dépasser 255 caractères.");
        if (childId == null) throw new IllegalArgumentException("Un cadeau doit appartenir à un enfant.");
        if ((reservedBy == null) != (reservedAt == null))
            throw new IllegalArgumentException("Les informations de réservation doivent être complètes.");
        title = title.trim();
    }

    public boolean isReserved() { return reservedBy != null; }

    public Gift reserve(String firstName, LocalDateTime reservedAt) {
        if (isReserved()) throw new IllegalStateException("Ce cadeau est déjà réservé.");
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("Le prénom du réservant est obligatoire.");
        if (firstName.length() > 255) throw new IllegalArgumentException("Le prénom ne peut pas dépasser 255 caractères.");
        if (reservedAt == null) throw new IllegalArgumentException("La date de réservation est obligatoire.");
        return new Gift(id, title, description, imageUrl, productUrl, firstName.trim(), reservedAt, childId);
    }

    public Gift cancelReservation() {
        if (!isReserved()) return this;
        return new Gift(id, title, description, imageUrl, productUrl, null, null, childId);
    }
}
