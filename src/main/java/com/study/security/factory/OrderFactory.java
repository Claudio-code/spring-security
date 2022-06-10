package com.study.security.factory;

import com.study.security.model.Client;
import com.study.security.model.ItemOrder;
import com.study.security.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderFactory {
    public static Order make() {
        final Date currentDate = new Date();
        return Order
                .builder()
                .createdAt(currentDate)
                .updatedAt(currentDate)
                .build();
    }
}
