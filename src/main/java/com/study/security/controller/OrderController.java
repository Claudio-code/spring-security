package com.study.security.controller;

import com.study.security.dto.order.OrderRequestDTO;
import com.study.security.exception.ErrorHandler;
import com.study.security.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController extends ErrorHandler {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        orderService.create(orderRequestDTO);
    }
}
