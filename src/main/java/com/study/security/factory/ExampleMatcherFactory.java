package com.study.security.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleMatcherFactory {
    public static ExampleMatcher make() {
        return ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    }
}
