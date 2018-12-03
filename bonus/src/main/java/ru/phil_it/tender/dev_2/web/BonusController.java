package ru.phil_it.tender.dev_2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.phil_it.tender.dev_2.domain.dto.NewBill;
import ru.phil_it.tender.dev_2.service.impl.BonusControlService;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Created by gennady on 28/11/18.
 */
@Slf4j
@RestController
@RequestMapping("/bonus")
public class BonusController {
    private final BonusControlService service;

    @Autowired
    public BonusController(BonusControlService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> correctBonusPoints(@RequestBody NewBill newBill)
            throws CardNumberNotFound {
        service.correctBonusPoints(newBill);
        return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBonusBalance(@PathVariable("cardId") Integer cardId)
            throws CardNumberNotFound {
        log.info("Asked balance for card " + cardId);
        return new ResponseEntity<Object>(service.getCardBalance(cardId), HttpStatus.OK);
    }

    //TODO add health check with actuator

    //Скорее всего эта хуета работает не так и эти эндпоинты чисто потестить
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewBill(@RequestBody NewBill newBill){
        throw new NotImplementedException();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> takeBonus(){
        throw new NotImplementedException();
    }
}
