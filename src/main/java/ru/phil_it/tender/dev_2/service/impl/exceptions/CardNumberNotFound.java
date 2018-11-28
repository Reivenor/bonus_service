package ru.phil_it.tender.dev_2.service.impl.exceptions;

import org.slf4j.Logger;

/**
 * Created by gennady on 28/11/18.
 */
public class CardNumberNotFound extends Throwable {
    private static final String message = "Client card with number %d not found";

    public CardNumberNotFound(Integer cardId, Logger log){
        super(String.format(message, cardId));
        log.error(String.format(message, cardId));
    }
}
