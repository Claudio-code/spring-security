package com.study.security.controller;

import com.study.security.dto.order.OrderRequestDTO;
import com.study.security.exception.ErrorHandler;
import com.study.security.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController extends ErrorHandler {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        orderService.create(orderRequestDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO orderRequestDTO) {
         orderService.update(id, orderRequestDTO);
    }
}
