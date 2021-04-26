package com.aw.tutorial.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
//    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String sampleApiRequest() {
        logger.info("Sample API call received");
//        final ResponseEntity<String> response =
//                new RestTemplate().getForEntity("http://localhost:8080/dummy-call", String.class);
//        return response.getBody();
        return "response";
    }

    public String hardCodedResponse(Exception ex) {
        return "Service unavailable - " + ex.getMessage();
    }
}
