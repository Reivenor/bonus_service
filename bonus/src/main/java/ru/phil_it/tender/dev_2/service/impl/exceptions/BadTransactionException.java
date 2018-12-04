package ru.phil_it.tender.dev_2.service.impl.exceptions;

import org.slf4j.Logger;

public class BadTransactionException extends Throwable{
    public final static String message = "Bad bill transaction happend. Check request";

    public BadTransactionException(Logger log){
        super(message);
        log.error(message);
    }
}
