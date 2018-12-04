package ru.phil_it.tender.dev_2.service.impl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyFieldsValidator.class)
public @interface ListElementsValidation {

    String message() default "Positions must contain any records and all records must be greater than zero.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}


