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
import ru.phil_it.tender.dev_2.domain.dto.NewBill;

@SpringBootApplication
@RestController
@Slf4j
@RequestMapping("/bonus")
@RibbonClient(name = "bonus", configuration = DispatcherControllerConfiguration.class)
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
    public ResponseEntity<Object> correctBonusPoints(@RequestBody NewBill newBill) {
        log.info("New bill");
        //todo надо тестить работает ли
        return this.restTemplate.postForEntity("http://bonus/bonus", newBill, Object.class);
    }

    @GetMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBonusBalance(@PathVariable("cardId") Integer cardId) {
        log.info("Asked balance for card " + cardId);
        return this.restTemplate.getForEntity("http://bonus/bonus/" + cardId, Object.class);
    }

}
