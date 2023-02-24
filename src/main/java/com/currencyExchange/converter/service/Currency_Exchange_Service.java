package com.currencyExchange.converter.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Currency_Exchange_Service {

    Map<String,Double> valueinUSD (String date, List<String> currency) throws IOException, InterruptedException;
    String getInfo(String currency) throws IOException, InterruptedException;
}
