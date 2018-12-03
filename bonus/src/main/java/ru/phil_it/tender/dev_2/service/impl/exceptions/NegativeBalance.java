package ru.phil_it.tender.dev_2.service.impl.exceptions;

import org.slf4j.Logger;

public class NegativeBalance extends Throwable {
        private static final String message = "Client card with number %d has not enough bonuses";

        public NegativeBalance(Integer cardId, Logger log){
            super(String.format(message, cardId));
            log.error(String.format(message, cardId));
        }
    }
