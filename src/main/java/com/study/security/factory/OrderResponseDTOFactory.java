package com.study.security.factory;

import com.study.security.dto.client.ClientResponseDTO;
import com.study.security.dto.order.OrderResponseDTO;
import com.study.security.dto.order.item.ItemResponseDTO;
import com.study.security.dto.product.ProductResponseDTO;
import com.study.security.model.ItemOrder;
import com.study.security.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseDTOFactory {
    public static OrderResponseDTO make(Order order) {
        final ClientResponseDTO clientResponseDTO = ClientResponseDTOFactory
                .makeClientResponseDTO(order.getClient());
        final List<ItemResponseDTO> itemResponseDTOList = order
                .getItemOrders()
                .stream()
                .map(OrderResponseDTOFactory::makeItemOrder)
                .toList();
        final int total = itemResponseDTOList.size();
        return new OrderResponseDTO(order.getId(), clientResponseDTO, total, itemResponseDTOList);
    }

    private static ItemResponseDTO makeItemOrder(ItemOrder itemOrder) {
        final ProductResponseDTO productResponseDTO = ProductResponseDTOFactory
                .make(itemOrder.getProduct());
        return new ItemResponseDTO(productResponseDTO, itemOrder.getAmount());
    }
}
