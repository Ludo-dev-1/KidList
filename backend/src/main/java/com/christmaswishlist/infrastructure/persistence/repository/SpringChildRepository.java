package com.christmaswishlist.infrastructure.persistence.repository;

import com.christmaswishlist.infrastructure.persistence.entity.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringChildRepository extends JpaRepository<ChildEntity, Long> { }
