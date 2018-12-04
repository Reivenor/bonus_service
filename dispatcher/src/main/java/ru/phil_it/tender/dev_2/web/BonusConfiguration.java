package ru.phil_it.tender.dev_2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.AvailabilityFilteringRule;

public class BonusConfiguration {

    private final IClientConfig ribbonClientConfig;

    @Autowired
    public BonusConfiguration(IClientConfig ribbonClientConfig) {
        this.ribbonClientConfig = ribbonClientConfig;
    }

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
