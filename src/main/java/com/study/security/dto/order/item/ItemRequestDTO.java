package com.study.security.dto.order.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDTO {
    @NotNull(message = "product must not be empty")
    @JsonProperty("product")
    private Long productId;

    @NotNull(message = "quantity must not be empty")
    private Integer quantity;
}
