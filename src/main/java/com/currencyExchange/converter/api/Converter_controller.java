package com.currencyExchange.converter.api;

import com.currencyExchange.converter.service.Currency_Exchange_Service;
import org.aspectj.weaver.ast.Call;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.KeyStore;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
public class Converter_controller {

    @Autowired
    private Currency_Exchange_Service currency_exchange_service;

    @GetMapping("/getInfo")
    public ResponseEntity<String> getInfo(@RequestParam String currency) throws IOException, InterruptedException {
        return ResponseEntity.ok(currency_exchange_service.getInfo((currency)));
    }

    @GetMapping("/converted-rates")
    public ResponseEntity<Map<String, Double>> getByDate(@RequestParam(required = false) String date) throws ExecutionException, InterruptedException {
        if(date == null){
            date = LocalDate.now().toString();
        }
        List<String> currencies = Arrays.asList("AED", "CAD", "EUR", "INR", "JPY");

        Map<String, Double> exchangeRates = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(currencies.size());
        String finalDate = date;
        List<Callable<Map.Entry<String,Double>>> tasks = currencies.stream().map(currency-> {
            return (Callable<Map.Entry<String, Double>>)() ->{
                try{
                    Double rate = currency_exchange_service.valueinUSD(finalDate, Collections.singletonList(currency)).get(currency);
                    return new AbstractMap.SimpleEntry<>(currency,rate);
                } catch (IOException | InterruptedException e){
                    e.printStackTrace();
                    return null;
                }
            };
            
        }).collect(Collectors.toList());
        List<Future<Map.Entry<String,Double>>> futures = executorService.invokeAll(tasks,20, TimeUnit.SECONDS);

        for(Future<Map.Entry<String,Double>> future: futures) {
            Map.Entry<String, Double> entry = future.get();
            if(entry != null){
                exchangeRates.put(entry.getKey(), entry.getValue());
            }
        }

        executorService.shutdown();
        return ResponseEntity.ok().header("timeout", "true").body(exchangeRates);
    }
}
