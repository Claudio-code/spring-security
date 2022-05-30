package com.study.security.dto.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {
    @NotEmpty(message = "name must not be empty")
    private String name;
    @NotEmpty(message = "description must not be empty")
    private String description;
    @NotNull(message = "price must not be empty")
    private BigDecimal price;
}
