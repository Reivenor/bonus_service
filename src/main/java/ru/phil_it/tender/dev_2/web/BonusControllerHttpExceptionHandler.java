package ru.phil_it.tender.dev_2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;

import java.util.HashMap;

/**
 * Created by gennady on 28/11/18.
 */
@Slf4j
@ControllerAdvice
public class BonusControllerHttpExceptionHandler {
    @ExceptionHandler(value = {CardNumberNotFound.class})
    public ResponseEntity<Object> wrongOrMissingCardNumberGiven(CardNumberNotFound exception){
        HashMap responce = new HashMap();
        responce.put("message", exception.getMessage());
        responce.put("error_code", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<Object>(responce, HttpStatus.BAD_REQUEST);
    }
}
