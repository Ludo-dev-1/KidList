package com.christmaswishlist.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class GiftTest {
    private final Gift gift = new Gift(1L, "Livre", null, null, null, null, null, 2L);

    @Test void reserveRecordsNameAndTime() {
        LocalDateTime now = LocalDateTime.now();
        Gift reserved = gift.reserve(" Mamie ", now);
        assertEquals("Mamie", reserved.reservedBy());
        assertEquals(now, reserved.reservedAt());
        assertTrue(reserved.isReserved());
    }

    @Test void cannotReserveTwice() {
        assertThrows(IllegalStateException.class, () -> gift.reserve("Mamie", LocalDateTime.now())
                .reserve("Tonton", LocalDateTime.now()));
    }

    @Test void requiresGiftTitleAndChild() {
        assertThrows(IllegalArgumentException.class, () -> new Gift(1L, " ", null, null, null, null, null, 2L));
        assertThrows(IllegalArgumentException.class, () -> new Gift(1L, "Livre", null, null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Gift(1L, "Livre", null, null, null, "Mamie", null, 2L));
        assertThrows(IllegalArgumentException.class, () -> new Gift(1L, "x".repeat(256), null, null, null, null, null, 2L));
    }

    @Test void rejectsInvalidReservationAndCanCancel() {
        assertThrows(IllegalArgumentException.class, () -> gift.reserve(" ", LocalDateTime.now()));
        assertThrows(IllegalArgumentException.class, () -> gift.reserve("Mamie", null));
        assertThrows(IllegalArgumentException.class, () -> gift.reserve("x".repeat(256), LocalDateTime.now()));
        assertSame(gift, gift.cancelReservation());
        assertFalse(gift.reserve("Mamie", LocalDateTime.now()).cancelReservation().isReserved());
    }
}
