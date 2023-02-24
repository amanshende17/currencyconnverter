package com.currencyExchange.converter;

import com.currencyExchange.converter.service.Currency_Exchange_Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest
class ConverterApplicationTests {
	@Autowired
	private Currency_Exchange_Service exchangeService;

	@Test
	public void testFetchExchangeRates() throws Exception {
		Map<String, Double> exchangeRates = exchangeService.valueinUSD("2020-01-01", Arrays.asList("AED", "CAD", "EUR", "INR", "JPY"));
		System.out.println(exchangeRates);
	}

	@Test
	public void testGetData() throws Exception {
		String data = exchangeService.getInfo("INR");
		System.out.println(data);
	}


}

