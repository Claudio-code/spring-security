package com.study.security.service;

import com.study.security.dto.order.item.ItemRequestDTO;
import com.study.security.factory.ItemOrderFactory;
import com.study.security.model.ItemOrder;
import com.study.security.model.Order;
import com.study.security.model.Product;
import com.study.security.repository.ItemOrderRepository;
import com.study.security.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ItemOrderService {
    private final ProductRepository productRepository;
    private final ItemOrderRepository itemOrderRepository;

    public void delete(ItemOrder itemOrder) {
        itemOrderRepository.delete(itemOrder);
    }

    public ItemOrder process(ItemRequestDTO itemRequestDTO, Order order) {
        final Product product = productRepository.findById(itemRequestDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final Optional<ItemOrder> itemOrderFound = findOne(product, order);
        if (itemOrderFound.isPresent()) {
            return update(itemOrderFound.get(), itemRequestDTO);
        }
        return create(product, itemRequestDTO, order);
    }

    private ItemOrder create(Product product, ItemRequestDTO itemRequestDTO, Order order) {
        final ItemOrder itemOrderToSave = ItemOrderFactory.make(product, itemRequestDTO, order);
        itemOrderRepository.save(itemOrderToSave);
        return itemOrderToSave;
    }

    private ItemOrder update(ItemOrder itemOrderToUpdate, ItemRequestDTO itemRequestDTO) {
        itemOrderToUpdate.setAmount(itemRequestDTO.getQuantity());
        itemOrderRepository.save(itemOrderToUpdate);
        return itemOrderToUpdate;
    }

    private Optional<ItemOrder> findOne(Product product, Order order) {
        final ItemOrder itemOrderToSave = ItemOrderFactory.make(product, order);
        return itemOrderRepository.findOne(Example.of(itemOrderToSave));
    }
}
