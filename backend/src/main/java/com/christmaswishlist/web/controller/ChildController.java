package com.christmaswishlist.web.controller;

import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.model.Gift;
import com.christmaswishlist.domain.port.in.ChildUseCases;
import com.christmaswishlist.domain.port.in.GiftUseCases;
import com.christmaswishlist.web.dto.ChildRequest;
import com.christmaswishlist.web.dto.ChildResponse;
import com.christmaswishlist.web.dto.GiftRequest;
import com.christmaswishlist.web.dto.GiftResponse;
import com.christmaswishlist.web.mapper.ApiMapper;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/children")
public class ChildController {
    private final ChildUseCases children;
    private final GiftUseCases gifts;
    private final ApiMapper mapper;

    public ChildController(ChildUseCases children, GiftUseCases gifts, ApiMapper mapper) {
        this.children = children;
        this.gifts = gifts;
        this.mapper = mapper;
    }

    @GetMapping public List<ChildResponse> getAll() { return children.getAllChildren().stream().map(mapper::toResponse).toList(); }
    @GetMapping("/{id}") public ChildResponse get(@PathVariable Long id) { return mapper.toResponse(children.getChild(id)); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ChildResponse create(@Valid @RequestBody ChildRequest request) {
        Child child = children.createChild(request.firstName(), request.age());
        return mapper.toResponse(child);
    }
    @PutMapping("/{id}") public ChildResponse update(@PathVariable Long id, @Valid @RequestBody ChildRequest request) {
        return mapper.toResponse(children.updateChild(id, request.firstName(), request.age()));
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { children.deleteChild(id); }
    @GetMapping("/{id}/gifts") public List<GiftResponse> getGifts(@PathVariable Long id) {
        return gifts.getGiftsByChild(id).stream().map(mapper::toResponse).toList();
    }
    @PostMapping("/{id}/gifts") @ResponseStatus(HttpStatus.CREATED)
    public GiftResponse createGift(@PathVariable Long id, @Valid @RequestBody GiftRequest request) {
        return mapper.toResponse(gifts.createGift(id, request.title(), request.description(), request.imageUrl(), request.productUrl()));
    }
}
