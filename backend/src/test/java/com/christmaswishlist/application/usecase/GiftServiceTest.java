package com.christmaswishlist.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.christmaswishlist.domain.model.Gift;
import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.port.out.ChildRepository;
import com.christmaswishlist.domain.port.out.GiftRepository;
import com.christmaswishlist.domain.exception.BusinessException;
import com.christmaswishlist.domain.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GiftServiceTest {
    private GiftRepository gifts;
    private ChildRepository children;
    private GiftService service;
    private final Gift available = new Gift(5L, "Puzzle", null, null, null, null, null, 1L);

    @BeforeEach void setUp() {
        gifts = mock(GiftRepository.class);
        children = mock(ChildRepository.class);
        service = new GiftService(gifts, children);
        when(gifts.findById(5L)).thenReturn(Optional.of(available));
        when(children.findById(1L)).thenReturn(Optional.of(new Child(1L, "Emma", 5)));
        when(gifts.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test void createsUpdatesDeletesAndFindsGifts() {
        when(gifts.updateDetails(any())).thenAnswer(invocation -> {
            Gift updated = invocation.getArgument(0);
            when(gifts.findById(5L)).thenReturn(Optional.of(updated));
            return true;
        });
        Gift created = service.createGift(1L, "Livre", "Description", null, null);
        assertEquals("Livre", created.title());
        Gift updated = service.updateGift(5L, "Puzzle géant", null, null, null);
        assertEquals("Puzzle géant", updated.title());
        assertEquals(1L, updated.childId());
        assertEquals(updated, service.getGift(5L));
        when(gifts.findByChildId(1L)).thenReturn(List.of(available));
        assertEquals(List.of(available), service.getGiftsByChild(1L));
        service.deleteGift(5L);
        verify(gifts).deleteById(5L);
    }

    @Test void requiresAnExistingChildWhenCreatingOrListingGifts() {
        when(children.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.createGift(2L, "Livre", null, null, null));
        assertThrows(ResourceNotFoundException.class, () -> service.getGiftsByChild(2L));
    }

    @Test void reservationPersistsGift() {
        when(gifts.reserveIfAvailable(any())).thenReturn(true);
        Gift result = service.reserveGift(5L, "Papa");
        assertEquals("Papa", result.reservedBy());
        verify(gifts).reserveIfAvailable(argThat(g -> g.reservedAt() != null));
    }

    @Test void concurrentReservationIsRejected() {
        when(gifts.reserveIfAvailable(any())).thenReturn(false);
        assertThrows(BusinessException.class, () -> service.reserveGift(5L, "Mamie"));
    }

    @Test void cancellationClearsReservation() {
        Gift reserved = available.reserve("Papa", LocalDateTime.now());
        when(gifts.findById(5L)).thenReturn(Optional.of(reserved));
        when(gifts.cancelReservation(reserved)).thenReturn(true);
        Gift result = service.cancelReservation(5L);
        assertFalse(result.isReserved());
        verify(gifts).cancelReservation(reserved);
    }

    @Test void cancellingAnAvailableGiftDoesNotWrite() {
        assertSame(available, service.cancelReservation(5L));
        verify(gifts, never()).save(any());
    }

    @Test void concurrentReservationIsNotClearedByCancellation() {
        Gift reserved = available.reserve("Papa", LocalDateTime.now());
        Gift newerReservation = available.reserve("Mamie", LocalDateTime.now().plusSeconds(1));
        when(gifts.findById(5L)).thenReturn(Optional.of(reserved), Optional.of(newerReservation));
        when(gifts.cancelReservation(reserved)).thenReturn(false);
        assertEquals(newerReservation, service.cancelReservation(5L));
    }

    @Test void missingGiftIsReportedForReadsAndWrites() {
        when(gifts.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getGift(99L));
        assertThrows(ResourceNotFoundException.class, () -> service.updateGift(99L, "Livre", null, null, null));
        assertThrows(ResourceNotFoundException.class, () -> service.deleteGift(99L));
        assertThrows(ResourceNotFoundException.class, () -> service.reserveGift(99L, "Papa"));
        assertThrows(ResourceNotFoundException.class, () -> service.cancelReservation(99L));
    }
}
