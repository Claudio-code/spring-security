package com.study.security.factory;

import com.study.security.enuns.OrderStatusEnum;
import com.study.security.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderFactory {
    public static Order make() {
        return Order
                .builder()
                .status(OrderStatusEnum.PROGRESS)
                .build();
    }
}
