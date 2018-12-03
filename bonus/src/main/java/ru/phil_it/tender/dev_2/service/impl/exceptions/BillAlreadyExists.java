package ru.phil_it.tender.dev_2.service.impl.exceptions;

import org.slf4j.Logger;

public class BillAlreadyExists extends Throwable {
    private static final String message = "Bill with number %d already exists";

    public BillAlreadyExists(Integer billId, Logger log){
        super(String.format(message, billId));
        log.error(String.format(message, billId));
    }
}
