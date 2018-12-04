package ru.phil_it.tender.dev_2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.phil_it.tender.dev_2.service.impl.exceptions.BadTransactionException;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by gennady on 28/11/18.
 */
@Slf4j
@RestControllerAdvice
public class BonusControllerHttpExceptionHandler {
    @ExceptionHandler(value = {CardNumberNotFound.class, BadTransactionException.class})
    public ResponseEntity<Object> wrongOrMissingCardNumberGiven(Throwable exception){
        HashMap responce = new HashMap();
        responce.put("message", exception.getMessage());
        responce.put("error_code", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<Object>(responce, HttpStatus.BAD_REQUEST);
    }
}
