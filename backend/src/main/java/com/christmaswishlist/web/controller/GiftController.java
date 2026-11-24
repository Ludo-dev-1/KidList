package com.christmaswishlist.web.controller;

import com.christmaswishlist.domain.port.in.GiftUseCases;
import com.christmaswishlist.web.dto.GiftRequest;
import com.christmaswishlist.web.dto.GiftResponse;
import com.christmaswishlist.web.dto.ReservationRequest;
import com.christmaswishlist.web.mapper.ApiMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {
    private static final Logger log = LoggerFactory.getLogger(GiftController.class);
    private final GiftUseCases gifts;
    private final ApiMapper mapper;

    public GiftController(GiftUseCases gifts, ApiMapper mapper) { this.gifts = gifts; this.mapper = mapper; }

    @GetMapping("/{id}") public GiftResponse get(@PathVariable Long id) { return mapper.toResponse(gifts.getGift(id)); }
    @PutMapping("/{id}") public GiftResponse update(@PathVariable Long id, @Valid @RequestBody GiftRequest request) {
        return mapper.toResponse(gifts.updateGift(id, request.title(), request.description(), request.imageUrl(), request.productUrl()));
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { gifts.deleteGift(id); }
    @PostMapping("/{id}/reserve") public GiftResponse reserve(@PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        GiftResponse response = mapper.toResponse(gifts.reserveGift(id, request.reservedBy()));
        log.info("gift_reserved giftId={} reservedBy={}", id, response.reservedBy());
        return response;
    }
    @DeleteMapping("/{id}/reserve") public GiftResponse cancel(@PathVariable Long id) {
        GiftResponse response = mapper.toResponse(gifts.cancelReservation(id));
        log.info("gift_reservation_cancelled giftId={}", id);
        return response;
    }
}
