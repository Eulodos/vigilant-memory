package com.aw.tutorial.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    Optional<CurrencyExchange> findCurrencyExchangeByFromAndTo(String from, String to);
}
