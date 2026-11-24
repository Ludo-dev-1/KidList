package com.christmaswishlist.web.controller;

import static org.mockito.Mockito.*;
import com.christmaswishlist.domain.model.Child;
import com.christmaswishlist.domain.port.in.ChildUseCases;
import com.christmaswishlist.domain.port.in.GiftUseCases;
import com.christmaswishlist.web.dto.ChildResponse;
import com.christmaswishlist.web.mapper.ApiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChildController.class)
@Import(com.christmaswishlist.web.exception.GlobalExceptionHandler.class)
class ChildControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private ChildUseCases children;
    @MockBean private GiftUseCases gifts;
    @MockBean private ApiMapper mapper;

    @Test void getChildReturnsMappedChild() throws Exception {
        when(children.getChild(1L)).thenReturn(new Child(1L, "Emma", 5));
        when(mapper.toResponse(any(Child.class))).thenReturn(new ChildResponse(1L, "Emma", 5));
        mvc.perform(get("/api/children/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Emma"));
    }
}
