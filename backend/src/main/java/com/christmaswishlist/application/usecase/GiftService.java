package com.christmaswishlist.application.usecase;

import com.christmaswishlist.domain.model.Gift;
import com.christmaswishlist.domain.exception.BusinessException;
import com.christmaswishlist.domain.exception.ResourceNotFoundException;
import com.christmaswishlist.domain.port.in.GiftUseCases;
import com.christmaswishlist.domain.port.out.ChildRepository;
import com.christmaswishlist.domain.port.out.GiftRepository;
import java.time.LocalDateTime;
import java.util.List;

public class GiftService implements GiftUseCases {
    private final GiftRepository gifts;
    private final ChildRepository children;

    public GiftService(GiftRepository gifts, ChildRepository children) {
        this.gifts = gifts;
        this.children = children;
    }

    @Override public Gift createGift(Long childId, String title, String description, String imageUrl, String productUrl) {
        if (children.findById(childId).isEmpty()) throw new ResourceNotFoundException("Enfant introuvable : " + childId);
        return gifts.save(new Gift(null, title, description, imageUrl, productUrl, null, null, childId));
    }

    @Override public Gift updateGift(Long id, String title, String description, String imageUrl, String productUrl) {
        Gift existing = getGift(id);
        Gift updated = new Gift(id, title, description, imageUrl, productUrl,
                existing.reservedBy(), existing.reservedAt(), existing.childId());
        if (!gifts.updateDetails(updated)) throw new ResourceNotFoundException("Cadeau introuvable : " + id);
        return getGift(id);
    }

    @Override public void deleteGift(Long id) {
        getGift(id);
        gifts.deleteById(id);
    }

    @Override public Gift getGift(Long id) {
        return gifts.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cadeau introuvable : " + id));
    }

    @Override public List<Gift> getGiftsByChild(Long childId) {
        if (children.findById(childId).isEmpty()) throw new ResourceNotFoundException("Enfant introuvable : " + childId);
        return gifts.findByChildId(childId);
    }

    @Override public Gift reserveGift(Long id, String reservedBy) {
        Gift reserved = getGift(id).reserve(reservedBy, LocalDateTime.now());
        if (!gifts.reserveIfAvailable(reserved)) throw new BusinessException("Ce cadeau est déjà réservé.");
        return reserved;
    }

    @Override public Gift cancelReservation(Long id) {
        Gift gift = getGift(id);
        if (!gift.isReserved()) return gift;
        Gift cancelled = gift.cancelReservation();
        return gifts.cancelReservation(gift) ? cancelled : getGift(id);
    }
}
