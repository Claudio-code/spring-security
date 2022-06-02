package com.study.security.factory;

import com.study.security.dto.product.ProductRequestDTO;
import com.study.security.model.Product;

public class ProductFactory {
    public static Product make(ProductRequestDTO productRequestDTO) {
        return Product.builder()
            .name(productRequestDTO.getName())
            .description(productRequestDTO.getDescription())
            .price(productRequestDTO.getPrice())
            .build();
    }
}
