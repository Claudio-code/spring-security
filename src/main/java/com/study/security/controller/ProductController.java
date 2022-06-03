package com.study.security.controller;

import javax.validation.Valid;

import java.util.List;

import com.study.security.dto.product.ProductRequestDTO;
import com.study.security.dto.product.ProductResponseDTO;
import com.study.security.dto.product.ProductUpdateRequestDTO;
import com.study.security.exception.ErrorHandler;
import com.study.security.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
