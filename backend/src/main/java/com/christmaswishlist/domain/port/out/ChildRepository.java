package com.christmaswishlist.domain.port.out;

import com.christmaswishlist.domain.model.Child;
import java.util.List;
import java.util.Optional;

public interface ChildRepository {
    Child save(Child child);
    Optional<Child> findById(Long id);
    List<Child> findAll();
    void deleteById(Long id);
}
