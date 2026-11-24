package com.christmaswishlist.application.usecase;

import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.port.in.ChildUseCases;
import com.christmaswishlist.domain.port.out.ChildRepository;
import com.christmaswishlist.domain.exception.ResourceNotFoundException;
import java.util.List;

public class ChildService implements ChildUseCases {
    private final ChildRepository children;

    public ChildService(ChildRepository children) { this.children = children; }

    @Override public Child createChild(String firstName, int age) {
        return children.save(new Child(null, firstName, age));
    }

    @Override public Child updateChild(Long id, String firstName, int age) {
        getChild(id);
        return children.save(new Child(id, firstName, age));
    }

    @Override public void deleteChild(Long id) {
        getChild(id);
        children.deleteById(id);
    }

    @Override public Child getChild(Long id) {
        return children.findById(id).orElseThrow(() -> new ResourceNotFoundException("Enfant introuvable : " + id));
    }

    @Override public List<Child> getAllChildren() { return children.findAll(); }
}
