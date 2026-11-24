package com.christmaswishlist.infrastructure.persistence.repository;

import com.christmaswishlist.infrastructure.persistence.entity.GiftEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SpringGiftRepository extends JpaRepository<GiftEntity, Long> {
    List<GiftEntity> findAllByChildIdOrderById(Long childId);

    @Modifying
    @Transactional
    @Query("update GiftEntity g set g.reservedBy = :reservedBy, g.reservedAt = :reservedAt where g.id = :id and g.reservedBy is null")
    int reserveIfAvailable(@Param("id") Long id, @Param("reservedBy") String reservedBy,
                           @Param("reservedAt") LocalDateTime reservedAt);

    @Modifying
    @Transactional
    @Query("update GiftEntity g set g.title = :title, g.description = :description, g.imageUrl = :imageUrl, g.productUrl = :productUrl where g.id = :id")
    int updateDetails(@Param("id") Long id, @Param("title") String title, @Param("description") String description,
                      @Param("imageUrl") String imageUrl, @Param("productUrl") String productUrl);

    @Modifying
    @Transactional
    @Query("update GiftEntity g set g.reservedBy = null, g.reservedAt = null where g.id = :id and g.reservedBy = :reservedBy and g.reservedAt = :reservedAt")
    int cancelReservation(@Param("id") Long id, @Param("reservedBy") String reservedBy,
                          @Param("reservedAt") LocalDateTime reservedAt);
}
