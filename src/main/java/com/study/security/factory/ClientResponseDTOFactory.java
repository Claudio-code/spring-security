package com.study.security.factory;

import com.study.security.dto.client.ClientResponseDTO;
import com.study.security.model.Client;
import lombok.Builder;

@Builder
public class ClientResponseDTOFactory implements FactoryInterface<ClientResponseDTO> {
    private Client client;

    @Override
    public ClientResponseDTO make() {
        return new ClientResponseDTO(client.getId(), client.getName());
    }

    public static ClientResponseDTO makeClientResponseDTO(Client client) {
        return ClientResponseDTOFactory.builder()
                .client(client)
                .build()
                .make();
    }
}
