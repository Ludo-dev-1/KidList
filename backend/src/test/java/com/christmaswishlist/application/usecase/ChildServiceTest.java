package com.christmaswishlist.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.christmaswishlist.domain.exception.ResourceNotFoundException;
import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.port.out.ChildRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChildServiceTest {
    private ChildRepository children;
    private ChildService service;

    @BeforeEach void setUp() {
        children = mock(ChildRepository.class);
        service = new ChildService(children);
        when(children.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test void createsAndUpdatesChildren() {
        Child created = service.createChild("Emma", 5);
        assertEquals(new Child(null, "Emma", 5), created);
        when(children.findById(3L)).thenReturn(Optional.of(new Child(3L, "Emma", 5)));
        assertEquals(new Child(3L, "Lucas", 8), service.updateChild(3L, "Lucas", 8));
    }

    @Test void rejectsUpdateForMissingChild() {
        when(children.findById(3L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateChild(3L, "Lucas", 8));
    }

    @Test void deletesOnlyExistingChild() {
        when(children.findById(3L)).thenReturn(Optional.of(new Child(3L, "Emma", 5)));
        service.deleteChild(3L);
        verify(children).deleteById(3L);
        when(children.findById(4L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteChild(4L));
        verify(children, never()).deleteById(4L);
    }

    @Test void retrievesOneAndAllChildren() {
        Child child = new Child(3L, "Emma", 5);
        when(children.findById(3L)).thenReturn(Optional.of(child));
        when(children.findAll()).thenReturn(List.of(child));
        assertEquals(child, service.getChild(3L));
        assertEquals(List.of(child), service.getAllChildren());
        when(children.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getChild(9L));
    }
}
