package com.study.security.factory;

import com.study.security.dto.order.item.ItemRequestDTO;
import com.study.security.model.ItemOrder;
import com.study.security.model.Order;
import com.study.security.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemOrderFactory {
    public static ItemOrder make(Product product, ItemRequestDTO itemRequestDTO, Order order) {
        return ItemOrder
                .builder()
                .product(product)
                .amount(itemRequestDTO.getQuantity())
                .order(order)
                .build();
    }

    public static ItemOrder make(Product product, Order order) {
        return ItemOrder
                .builder()
                .product(product)
                .order(order)
                .build();
    }
}
