package com.study.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.dto.product.ProductRequestDTO;
import com.study.security.dto.product.ProductResponseDTO;
import com.study.security.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {
    static String PRODUCT_API = "/products";
    static String PRODUCT_NAME = "chair";
    static String PRODUCT_DESCRIPTION = "The best chair in the world";
    static BigDecimal PRODUCT_PRICE = new BigDecimal(100);
    static Long PRODUCT_ID = 1L;

    @Autowired
    MockMvc mvc;
    @MockBean
    ProductService service;

    @Test
    void shouldCreateProductTest() throws Exception {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE);
        BDDMockito.given(service.create(Mockito.any(ProductRequestDTO.class))).willReturn(productResponseDTO);
        String json = new ObjectMapper().writeValueAsString(productRequestDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PRODUCT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value(productResponseDTO.name()))
                .andExpect(jsonPath("description").value(productResponseDTO.description()))
                .andExpect(jsonPath("price").value(productResponseDTO.price()));

    }

    @Test
    void shouldReturnExceptionIfNotSendParamsInBodyTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PRODUCT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{}");

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));
    }
}
