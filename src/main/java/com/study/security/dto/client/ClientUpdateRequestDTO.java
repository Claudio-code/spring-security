package com.study.security.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class ClientUpdateRequestDTO {
    @NotEmpty(message = "id must not be empty")
    private Long id;
    @NotEmpty(message = "name must not be empty")
    private String name;
}
