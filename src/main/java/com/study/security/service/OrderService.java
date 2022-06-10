package com.study.security.service;

import com.study.security.dto.order.OrderRequestDTO;
import com.study.security.factory.OrderFactory;
import com.study.security.model.Client;
import com.study.security.model.Order;
import com.study.security.repository.ClientRepository;
import com.study.security.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ItemOrderService itemOrderService;

    public void create(OrderRequestDTO orderRequestDTO) {
        final Order order = OrderFactory.make();
        createAndUpdateOrderAndItems(orderRequestDTO, order);
    }

    public void update(Long id, OrderRequestDTO orderRequestDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        createAndUpdateOrderAndItems(orderRequestDTO, order);
    }

    private void createAndUpdateOrderAndItems(OrderRequestDTO orderRequestDTO, Order order) {
        final Client client = clientRepository.findById(orderRequestDTO.getClientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        order.setClient(client);
        orderRepository.save(order);
        orderRequestDTO.getListItems()
                .forEach(itemRequestDTO -> itemOrderService.process(itemRequestDTO, order));
    }
}
