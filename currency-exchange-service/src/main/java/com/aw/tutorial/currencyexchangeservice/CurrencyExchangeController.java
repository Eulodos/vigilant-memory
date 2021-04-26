package com.aw.tutorial.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final Environment environment;
    private final CurrencyExchangeRepository currencyExchangeRepository;


    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        logger.info("retrieveExchangeValue called from {} to {}", from, to);
        CurrencyExchange currencyExchange = currencyExchangeRepository.findCurrencyExchangeByFromAndTo(from, to)
                .orElseThrow(() -> new RuntimeException("Could not find currency exchange " + from + " to " + to));
        final String port = environment.getProperty("server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
