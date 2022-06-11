package com.study.security.dto.order;

import com.study.security.dto.client.ClientResponseDTO;
import com.study.security.dto.order.item.ItemResponseDTO;

import java.util.List;

public record OrderResponseDTO(
        Long id,
        ClientResponseDTO client,
        Integer total,
        List<ItemResponseDTO> listItems
) {
}
