package com.study.security.controller;

import javax.validation.Valid;

import java.util.List;

import com.study.security.dto.client.ClientRequestDTO;
import com.study.security.dto.client.ClientResponseDTO;
import com.study.security.dto.client.ClientUpdateRequestDTO;
import com.study.security.exception.ErrorHandler;
import com.study.security.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController extends ErrorHandler {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponseDTO> findAll() {
        return clientService.findAll();
    }

    @GetMapping("{id}")
    public ClientResponseDTO findOne(@PathVariable Long id) {
        return clientService.finOneById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO create(@RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        return clientService.create(clientRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ClientResponseDTO update(@RequestBody @Valid ClientUpdateRequestDTO clientUpdateRequestDTO) {
        return clientService.updateAllClient(clientUpdateRequestDTO);
    }

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponseDTO> search(@RequestBody @Valid ClientUpdateRequestDTO clientUpdateRequestDTO) {
        return clientService.search(clientUpdateRequestDTO);
    }
}
