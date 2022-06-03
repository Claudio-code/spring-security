package com.study.security.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ClientRequestDTO {
    @NotEmpty(message = "name must not be empty")
    private String name;
}
