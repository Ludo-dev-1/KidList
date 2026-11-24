package com.christmaswishlist.infrastructure.persistence.adapter;

import com.christmaswishlist.domain.model.Gift;
import com.christmaswishlist.domain.port.out.GiftRepository;
import com.christmaswishlist.infrastructure.persistence.entity.GiftEntity;
import com.christmaswishlist.infrastructure.persistence.repository.SpringGiftRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class GiftPersistenceAdapter implements GiftRepository {
    private final SpringGiftRepository repository;
    private final PersistenceMapper mapper;
    public GiftPersistenceAdapter(SpringGiftRepository repository, PersistenceMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    @Override public Gift save(Gift gift) { return mapper.toDomain(repository.save(mapper.toEntity(gift))); }
    @Override public Optional<Gift> findById(Long id) { return repository.findById(id).map(mapper::toDomain); }
    @Override public List<Gift> findByChildId(Long childId) {
        return repository.findAllByChildIdOrderById(childId).stream().map(mapper::toDomain).toList();
    }
    @Override public void deleteById(Long id) { repository.deleteById(id); }
    @Override public boolean reserveIfAvailable(Gift gift) {
        return repository.reserveIfAvailable(gift.id(), gift.reservedBy(), gift.reservedAt()) == 1;
    }
    @Override public boolean updateDetails(Gift gift) {
        return repository.updateDetails(gift.id(), gift.title(), gift.description(), gift.imageUrl(), gift.productUrl()) == 1;
    }
    @Override public boolean cancelReservation(Gift gift) {
        return repository.cancelReservation(gift.id(), gift.reservedBy(), gift.reservedAt()) == 1;
    }
}
