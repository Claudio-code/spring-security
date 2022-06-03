package com.study.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.dto.client.ClientRequestDTO;
import com.study.security.dto.client.ClientResponseDTO;
import com.study.security.dto.client.ClientUpdateRequestDTO;
import com.study.security.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc
class ClientControllerTest {
    static String CLIENT_API = "/clients";
    static String CLIENT_NAME = "nelson";
    static Long CLIENT_ID = 1L;

    @Autowired
    MockMvc mvc;
    @MockBean
    ClientService clientService;

    @Test
    void shouldCreateClientTest() throws Exception {
        ClientRequestDTO dto = new ClientRequestDTO(CLIENT_NAME);
        ClientResponseDTO responseDTO = new ClientResponseDTO(CLIENT_ID, CLIENT_NAME);
        BDDMockito.given(clientService.create(Mockito.any(ClientRequestDTO.class))).willReturn(responseDTO);
        String json = new ObjectMapper().writeValueAsString(dto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CLIENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value(responseDTO.name()));
    }

    @Test
    void shouldReturnExceptionIfNotSendClientNameInBodyTest() throws Exception {
        ClientResponseDTO responseDTO = new ClientResponseDTO(CLIENT_ID, CLIENT_NAME);
        BDDMockito.given(clientService.create(Mockito.any(ClientRequestDTO.class))).willReturn(responseDTO);
        String json = new ObjectMapper().writeValueAsString("");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CLIENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)));
    }

    @Test
    void shouldFindOneClientTest() throws Exception {
        String url = CLIENT_API + "/" + CLIENT_ID;
        ClientResponseDTO responseDTO = new ClientResponseDTO(CLIENT_ID, CLIENT_NAME);
        BDDMockito.given(clientService.finOneById(CLIENT_ID)).willReturn(responseDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value(responseDTO.name()));
    }

    @Test
    void shouldReturnExceptionIfNotHasClientInDatabaseTest() throws Exception {
        String url = CLIENT_API + "/" + CLIENT_ID;
        BDDMockito.given(clientService.finOneById(CLIENT_ID)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);

        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors", hasSize(1)));
    }

    @Test
    void shouldFindAllClientsTest() throws Exception {
        List<ClientResponseDTO> responseDTOList = List.of(
                new ClientResponseDTO(CLIENT_ID, CLIENT_NAME),
                new ClientResponseDTO(CLIENT_ID + 2L, CLIENT_NAME + " Silva")
        );
        BDDMockito.given(clientService.findAll()).willReturn(responseDTOList);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CLIENT_API);
        mvc.perform(request)
                .andExpect(status().isOk());

    }

    @Test
    void shouldDeleteClientTest() throws Exception {
        String url = CLIENT_API + "/" + CLIENT_ID;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(url);
        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldSearchClientTest() throws Exception {
        String url = CLIENT_API + "/search";
        ClientResponseDTO responseDTO = new ClientResponseDTO(CLIENT_ID, CLIENT_NAME);
        ClientUpdateRequestDTO clientUpdateRequestDTO = new ClientUpdateRequestDTO(CLIENT_ID, CLIENT_NAME);
        BDDMockito.given(clientService.search(Mockito.any(ClientUpdateRequestDTO.class))).willReturn(List.of(responseDTO));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clientUpdateRequestDTO));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(CLIENT_ID))
                .andExpect(jsonPath("$[0].name").value(responseDTO.name()));
    }

    @Test
    void shouldUpdateClientTest() throws Exception {
        ClientUpdateRequestDTO clientUpdateRequestDTO = new ClientUpdateRequestDTO(CLIENT_ID, CLIENT_NAME);
        ClientResponseDTO responseDTO = new ClientResponseDTO(CLIENT_ID, CLIENT_NAME);
        BDDMockito.given(clientService.updateAllClient(Mockito.any(ClientUpdateRequestDTO.class))).willReturn(responseDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(CLIENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clientUpdateRequestDTO));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(CLIENT_ID))
                .andExpect(jsonPath("name").value(responseDTO.name()));
    }
}
