package com.christmaswishlist.infrastructure.configuration;

import com.christmaswishlist.application.usecase.ChildService;
import com.christmaswishlist.application.usecase.GiftService;
import com.christmaswishlist.domain.port.in.ChildUseCases;
import com.christmaswishlist.domain.port.in.GiftUseCases;
import com.christmaswishlist.domain.port.out.ChildRepository;
import com.christmaswishlist.domain.port.out.GiftRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {
    @Bean
    ChildUseCases childUseCases(ChildRepository children) {
        return new ChildService(children);
    }

    @Bean
    GiftUseCases giftUseCases(GiftRepository gifts, ChildRepository children) {
        return new GiftService(gifts, children);
    }
}
