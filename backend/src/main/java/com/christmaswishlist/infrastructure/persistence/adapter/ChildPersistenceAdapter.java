package com.christmaswishlist.infrastructure.persistence.adapter;

import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.port.out.ChildRepository;
import com.christmaswishlist.infrastructure.persistence.repository.SpringChildRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ChildPersistenceAdapter implements ChildRepository {
    private final SpringChildRepository repository;
    private final PersistenceMapper mapper;
    public ChildPersistenceAdapter(SpringChildRepository repository, PersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    @Override public Child save(Child child) { return mapper.toDomain(repository.save(mapper.toEntity(child))); }
    @Override public Optional<Child> findById(Long id) { return repository.findById(id).map(mapper::toDomain); }
    @Override public List<Child> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    @Override public void deleteById(Long id) { repository.deleteById(id); }
}
