package ru.phil_it.tender.dev_2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@Slf4j
@SpringBootApplication
@RestController
@RibbonClient(name = "bonus", configuration = BonusConfiguration.class)
public class DispatcherController {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    public DispatcherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final RestTemplate restTemplate;

   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
           consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Object> correctBonusPoints(@RequestBody NewBillDto newBill) {
        log.info("New Bill transaction to redirect.");
       return this.restTemplate.postForEntity("http://bonus/bonus", newBill, Object.class);
    }

    @GetMapping(value = "bonus/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBonusBalance(@PathVariable("cardId") Integer cardId) {
        String uri = "http://bonus/bonus/" + cardId;
        return this.restTemplate.getForEntity(uri, Object.class);
    }

}
