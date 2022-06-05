package com.study.security.service;

import com.study.security.dto.order.OrderRequestDTO;
import com.study.security.factory.OrderFactory;
import com.study.security.model.Client;
import com.study.security.model.ItemOrder;
import com.study.security.model.Order;
import com.study.security.repository.ClientRepository;
import com.study.security.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ItemOrderService itemOrderService;

    public void create(OrderRequestDTO orderRequestDTO) {
        final Client client = clientRepository.findById(orderRequestDTO.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final List<ItemOrder> itemOrders = orderRequestDTO
                .getListItems()
                .stream()
                .map(itemOrderService::create)
                .toList();
        final Order order = OrderFactory.make(client, itemOrders);
        orderRepository.save(order);
    }
}
