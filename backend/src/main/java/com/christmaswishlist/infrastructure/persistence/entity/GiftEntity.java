package com.christmaswishlist.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gifts")
public class GiftEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 255) private String title;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(name = "image_url", columnDefinition = "TEXT") private String imageUrl;
    @Column(name = "product_url", columnDefinition = "TEXT") private String productUrl;
    @Column(name = "reserved_by", length = 255) private String reservedBy;
    @Column(name = "reserved_at") private LocalDateTime reservedAt;
    @Column(name = "child_id", nullable = false) private Long childId;
    protected GiftEntity() { }
    public GiftEntity(Long id, String title, String description, String imageUrl, String productUrl,
                      String reservedBy, LocalDateTime reservedAt, Long childId) {
        this.id = id; this.title = title; this.description = description; this.imageUrl = imageUrl;
        this.productUrl = productUrl; this.reservedBy = reservedBy; this.reservedAt = reservedAt; this.childId = childId;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getProductUrl() { return productUrl; }
    public String getReservedBy() { return reservedBy; }
    public LocalDateTime getReservedAt() { return reservedAt; }
    public Long getChildId() { return childId; }
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }
    public void setReservedBy(String reservedBy) { this.reservedBy = reservedBy; }
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }
    public void setChildId(Long childId) { this.childId = childId; }
}
