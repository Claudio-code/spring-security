package com.study.security.dto.order.item;

import com.study.security.dto.product.ProductResponseDTO;

public record ItemResponseDTO(
        ProductResponseDTO product,
        Integer quantity
) {
}
