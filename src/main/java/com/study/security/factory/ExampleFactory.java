package com.study.security.factory;

import com.study.security.model.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleFactory {
    public static Example<Client> make(Client client) {
        final ExampleMatcher exampleMatcher = ExampleMatcherFactory.make();
        return Example.of(client, exampleMatcher);
    }
}
