package com.study.security.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ClientRequestDTO {
    @NotEmpty(message = "name must not be empty")
    private String name;
}
