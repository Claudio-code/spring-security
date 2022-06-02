package com.study.security.factory;

import com.study.security.dto.product.ProductResponseDTO;
import com.study.security.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseDTOFactory {
    public static ProductResponseDTO make(Product product) {
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
    }
}
