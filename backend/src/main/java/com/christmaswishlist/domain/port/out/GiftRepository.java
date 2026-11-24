package com.christmaswishlist.domain.port.out;

import com.christmaswishlist.domain.model.Gift;
import java.util.List;
import java.util.Optional;

public interface GiftRepository {
    Gift save(Gift gift);
    Optional<Gift> findById(Long id);
    List<Gift> findByChildId(Long childId);
    void deleteById(Long id);
    boolean reserveIfAvailable(Gift gift);
    boolean updateDetails(Gift gift);
    boolean cancelReservation(Gift gift);
}
