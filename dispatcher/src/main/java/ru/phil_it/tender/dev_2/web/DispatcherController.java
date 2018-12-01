package ru.phil_it.tender.dev_2.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import ru.phil_it.tender.dev_2.domain.dto.NewBill;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;

@SpringBootApplication
@RestController
@RibbonClient(name = "say-hello", configuration = DispatcherControllerConfiguration.class)
public class DispatcherController {
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> correctBonusPoints(@RequestBody NewBill newBill)
            throws CardNumberNotFound {
        //todo not work
        return this.restTemplate.getForObject("http://say-hello/greeting", ResponseEntity.class);
    }
}
