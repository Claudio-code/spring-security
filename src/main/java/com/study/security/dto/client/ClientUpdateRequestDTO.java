package com.study.security.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ClientUpdateRequestDTO {
    @NotNull(message = "id must not be empty")
    private Long id;
    @NotEmpty(message = "name must not be empty")
    private String name;
}
