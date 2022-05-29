package com.study.security.service;


import com.study.security.dto.client.ClientRequestDTO;
import com.study.security.dto.client.ClientResponseDTO;
import com.study.security.dto.client.ClientUpdateRequestDTO;
import com.study.security.exception.ClientAlreadyExistsException;
import com.study.security.factory.ClientResponseDTOFactory;
import com.study.security.factory.ExampleFactory;
import com.study.security.model.Client;
import com.study.security.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientResponseDTOFactory::makeClientResponseDTO)
                .collect(Collectors.toList());
    }

    public ClientResponseDTO finOneById(Long id) {
        final Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ClientResponseDTOFactory.makeClientResponseDTO(client);
    }

    public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) {
        final Client client = Client.builder()
                .name(clientRequestDTO.getName())
                .build();
        if (clientRepository.findOne(Example.of(client)).isPresent()) {
            throw new ClientAlreadyExistsException();
        }
        clientRepository.save(client);
        return ClientResponseDTOFactory.makeClientResponseDTO(client);
    }

    public void delete(Long id) {
        final Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        clientRepository.delete(client);
    }

    public ClientResponseDTO updateAllClient(ClientUpdateRequestDTO clientUpdateRequestDTO) {
        final Client client = clientRepository.findById(clientUpdateRequestDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        client.setName(clientUpdateRequestDTO.getName());
        clientRepository.save(client);
        return ClientResponseDTOFactory.makeClientResponseDTO(client);
    }

    public List<ClientResponseDTO> search(ClientUpdateRequestDTO clientUpdateRequestDTO) {
        final Client clientToSearch = Client.builder()
                .id(clientUpdateRequestDTO.getId())
                .name(clientUpdateRequestDTO.getName())
                .build();
        Example<Client> exampleClient = ExampleFactory.make(clientToSearch);
        List<Client> clients = clientRepository.findAll(exampleClient);
        return clients.stream().map(ClientResponseDTOFactory::makeClientResponseDTO)
                .collect(Collectors.toList());
    }
}
