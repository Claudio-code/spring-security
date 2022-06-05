package com.study.security.service;

import com.study.security.dto.order.item.ItemRequestDTO;
import com.study.security.factory.ItemOrderFactory;
import com.study.security.model.ItemOrder;
import com.study.security.model.Product;
import com.study.security.repository.ItemOrderRepository;
import com.study.security.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class ItemOrderService {
    private final ProductRepository productRepository;
    private final ItemOrderRepository itemOrderRepository;

    public ItemOrder create(ItemRequestDTO itemRequestDTO) {
        final Product product = productRepository.findById(itemRequestDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final ItemOrder itemOrder = ItemOrderFactory.make(product, itemRequestDTO);
        itemOrderRepository.save(itemOrder);
        return itemOrder;
    }
}
