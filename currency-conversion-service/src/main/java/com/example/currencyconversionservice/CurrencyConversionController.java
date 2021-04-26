package com.example.currencyconversionservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy currencyExchangeProxy;

    public CurrencyConversionController(CurrencyExchangeProxy currencyExchangeProxy) {
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{qty}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
                                                          @PathVariable BigDecimal qty) {

        final Map<String, String> uriVariables = Map.of("from", from, "to", to);

        final ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);
        final CurrencyConversion currencyConversion = responseEntity.getBody();
        if (currencyConversion == null) {
            throw new RuntimeException("Could not fetch exchange data from " + from + " to " + to);
        }
        return new CurrencyConversion(currencyConversion.getId(), from, to, currencyConversion.getEnvironment(),
                qty, currencyConversion.getConversionMultiple(), qty.multiply(currencyConversion.getConversionMultiple()));
    }


    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{qty}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
                                                               @PathVariable BigDecimal qty) {
        final CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(), from, to, currencyConversion.getEnvironment(),
                qty, currencyConversion.getConversionMultiple(), qty.multiply(currencyConversion.getConversionMultiple()));
    }
}
