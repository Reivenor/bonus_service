package ru.phil_it.tender.dev_2.service.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyFieldsValidator implements ConstraintValidator<ListElementsValidation, List<Long>> {

    @Override
    public void initialize(ListElementsValidation notEmptyFields) {
    }

    @Override
    public boolean isValid(List<Long> objects, ConstraintValidatorContext context) {
        return !objects.isEmpty() && (objects.stream().filter(value -> value > 0L).count() == objects.size() );
    }

}
