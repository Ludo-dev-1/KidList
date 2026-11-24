package com.christmaswishlist.domain.port.in;

import com.christmaswishlist.domain.model.Gift;
import java.util.List;

public interface GiftUseCases {
    Gift createGift(Long childId, String title, String description, String imageUrl, String productUrl);
    Gift updateGift(Long id, String title, String description, String imageUrl, String productUrl);
    void deleteGift(Long id);
    Gift getGift(Long id);
    List<Gift> getGiftsByChild(Long childId);
    Gift reserveGift(Long id, String reservedBy);
    Gift cancelReservation(Long id);
}
