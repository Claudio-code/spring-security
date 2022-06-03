package com.study.security.service;

import java.util.List;

import com.study.security.dto.product.ProductRequestDTO;
import com.study.security.dto.product.ProductResponseDTO;
import com.study.security.dto.product.ProductUpdateRequestDTO;
import com.study.security.exception.ProductAlreadyExistsException;
import com.study.security.factory.ProductFactory;
import com.study.security.factory.ProductResponseDTOFactory;
import com.study.security.model.Product;
import com.study.security.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        final Product product = ProductFactory.make(productRequestDTO);
        if (productRepository.findOne(Example.of(product)).isPresent()) {
            throw new ProductAlreadyExistsException();
        }
        productRepository.save(product);
        return ProductResponseDTOFactory.make(product);
    }

    public ProductResponseDTO finOneById(Long id) {
        final Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ProductResponseDTOFactory.make(product);
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(ProductResponseDTOFactory::make)
            .toList();
    }

    public void delete(Long id) {
        final Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        productRepository.delete(product);
    }

    public ProductResponseDTO update(ProductUpdateRequestDTO productUpdateRequestDTO) {
        final Product product = productRepository.findById(productUpdateRequestDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        product.setName(productUpdateRequestDTO.getName());
        product.setDescription(productUpdateRequestDTO.getDescription());
        product.setPrice(productUpdateRequestDTO.getPrice());
        productRepository.save(product);
        return ProductResponseDTOFactory.make(product);
    }

    public ProductResponseDTO patch(ProductUpdateRequestDTO productUpdateRequestDTO) {
        final Product product = productRepository.findById(productUpdateRequestDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!product.getName().equals(productUpdateRequestDTO.getName())) {
            product.setName(productUpdateRequestDTO.getName());
        }
        if (!product.getDescription().equals(productUpdateRequestDTO.getDescription())) {
            product.setDescription(productUpdateRequestDTO.getDescription());
        }
        if (!product.getPrice().equals(productUpdateRequestDTO.getPrice())) {
            product.setPrice(productUpdateRequestDTO.getPrice());
        }
        productRepository.save(product);
        return ProductResponseDTOFactory.make(product);
    }
}
