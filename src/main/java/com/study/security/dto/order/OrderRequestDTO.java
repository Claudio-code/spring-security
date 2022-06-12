package com.study.security.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.security.dto.order.item.ItemRequestDTO;
import com.study.security.enuns.OrderStatusEnum;
import com.study.security.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDTO {
    @NotNull(message = "client must not be empty")
    @JsonProperty("client")
    private Long clientId;

    @NotNull(message = "total must not be empty")
    private Integer total;

    @NotEmptyList
    @JsonProperty("items")
    private List<ItemRequestDTO> listItems;

    private OrderStatusEnum status;
}
