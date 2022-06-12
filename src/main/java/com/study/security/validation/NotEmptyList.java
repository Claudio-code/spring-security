package com.study.security.validation;

import com.study.security.validation.constraint.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {
    String message() default "List must not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
