package com.study.security.service;

import com.study.security.dto.order.OrderRequestDTO;
import com.study.security.dto.order.OrderResponseDTO;
import com.study.security.factory.OrderFactory;
import com.study.security.factory.OrderResponseDTOFactory;
import com.study.security.model.Client;
import com.study.security.model.ItemOrder;
import com.study.security.model.Order;
import com.study.security.repository.ClientRepository;
import com.study.security.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ItemOrderService itemOrderService;

    public OrderResponseDTO findOne(Long id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return OrderResponseDTOFactory.make(order);
    }

    public List<OrderResponseDTO> findAll() {
        final List<Order> orderList = orderRepository.findAll();
        return orderList
                .stream()
                .map(OrderResponseDTOFactory::make)
                .toList();
    }

    public void create(OrderRequestDTO orderRequestDTO) {
        final Client client = clientRepository.findById(orderRequestDTO.getClientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        final Order order = OrderFactory.make();
        order.setClient(client);
        final List<ItemOrder> itemOrders = orderRequestDTO.getListItems()
                .stream().map(itemRequestDTO -> itemOrderService.process(itemRequestDTO, null)).toList();

        order.setItemOrders(itemOrders);
        orderRepository.save(order);
    }

    public void update(Long id, OrderRequestDTO orderRequestDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        createAndUpdateOrderAndItems(orderRequestDTO, order);
    }

    public void delete(Long id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        order.getItemOrders().forEach(itemOrderService::delete);
        orderRepository.delete(order);
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
