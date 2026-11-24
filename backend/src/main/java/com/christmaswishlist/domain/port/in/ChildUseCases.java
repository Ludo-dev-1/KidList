package com.christmaswishlist.domain.port.in;

import com.christmaswishlist.domain.model.Child;
import java.util.List;

public interface ChildUseCases {
    Child createChild(String firstName, int age);
    Child updateChild(Long id, String firstName, int age);
    void deleteChild(Long id);
    Child getChild(Long id);
    List<Child> getAllChildren();
}
