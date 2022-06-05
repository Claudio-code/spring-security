package com.study.security.controller;

import com.study.security.dto.product.ProductRequestDTO;
import com.study.security.dto.product.ProductResponseDTO;
import com.study.security.dto.product.ProductUpdateRequestDTO;
import com.study.security.exception.ErrorHandler;
import com.study.security.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController extends ErrorHandler {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.create(productRequestDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO update(@RequestBody @Valid ProductUpdateRequestDTO productUpdateRequestDTO) {
        return productService.update(productUpdateRequestDTO);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO patch(@RequestBody @Valid ProductUpdateRequestDTO productUpdateRequestDTO) {
        return productService.patch(productUpdateRequestDTO);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO findOne(@PathVariable Long id) {
        return productService.finOneById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> findAll() {
        return productService.findAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
