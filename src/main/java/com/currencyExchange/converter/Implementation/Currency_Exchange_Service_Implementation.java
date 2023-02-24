package com.currencyExchange.converter.Implementation;

import com.currencyExchange.converter.model.Audit_Info;
import com.currencyExchange.converter.service.Audit_Info_Service;
import com.currencyExchange.converter.service.Currency_Exchange_Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class Currency_Exchange_Service_Implementation implements Currency_Exchange_Service {

    @Autowired
    private Audit_Info_Service audit_info_service;

    @Override
    public Map<String, Double> valueinUSD(String date, List<String> currency_value) throws IOException, InterruptedException {
        Map<String, Double> exchangeRates = new HashMap<>();
        String BASE_CURRENCY = "USD";

        for(String currency : currency_value) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.apilayer.com/exchangerates_data/"+date+"?symbols="+ currency +"&base="+BASE_CURRENCY+""))
                    .header("apiKey", "FyKchylap90alH36wDCDX660ksddT00x")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonNode = new ObjectMapper().readTree(response.body());
            Double rate = jsonNode.get("rates").get(currency).asDouble();
            exchangeRates.put(currency, rate);
        }
        Audit_Info audit_info=new Audit_Info();
        Integer rand_Id = (int)(Math.random()*1000);
        audit_info.setRequest_Id((rand_Id));
        audit_info.setRequest("https://api.apilayer.com/exchangerates_data/"+date+"?symbols="+ currency_value +"&base="+BASE_CURRENCY+"");
        audit_info.setResponse(exchangeRates.toString());
        audit_info.setRequestStatus("Got-Response");
        if(audit_info_service.checkId(rand_Id)){
            audit_info_service.updateInfo(audit_info);
        }
        else{
            audit_info_service.createInfo(audit_info);
        }
        return exchangeRates;
    }

    @Override
    public String getInfo(String currency) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.apilayer.com/exchangerates_data/convert?to=USD&from="+currency+"&amount=1"))
                .header("apiKey", "FyKchylap90alH36wDCDX660ksddT00x")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
