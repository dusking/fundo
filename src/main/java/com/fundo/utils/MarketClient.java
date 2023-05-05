package com.fundo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundo.exception.MarketConnectionException;
import com.fundo.exception.invalidMarketRequestException;
import com.fundo.requests.MarketStockQuoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
public class MarketClient {

    @Autowired
    private Environment env;

    public MarketClient() {

    }

    public HashMap<String, String> marketClientInfo() {
        HashMap<String, String> clientInfo = new HashMap<>();
        try{
            clientInfo.put("host", env.getProperty("spring.api.host"));
            clientInfo.put("endpoint", env.getProperty("spring.api.endpoint"));
            clientInfo.put("key", env.getProperty("spring.api.key"));
            System.out.printf("############## %s", clientInfo);
        } catch(Exception e) {
            System.out.printf("############## %s", e.getMessage());
        }
        return clientInfo;
    }

    public void buy(String symbol, double amount) throws invalidMarketRequestException {
        if (amount < 0) {
            throw new invalidMarketRequestException();
        }
    }

    public void sell(String symbol, double amount) throws invalidMarketRequestException {
        if (amount < 0) {
            throw new invalidMarketRequestException();
        }
    }

    public double getStockQuote(String symbol) throws MarketConnectionException, JsonProcessingException {
//        System.out.printf("############## %s\n", marketClientInfo());

        return 10;
//        String rapidApiHost = "twelve-data1.p.rapidapi.com";
//        String RapidApiEndpoint = "https://twelve-data1.p.rapidapi.com/price";
//        String rapidApiKey = "oBvV4zrXWCmshhQrYwgfKYLtzPIEp1nfsjBjsnLNYBQDnQft8M";
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(RapidApiEndpoint + "?symbol=" + symbol + "&format=json&outputsize=30"))
//                .header("X-RapidAPI-Key", rapidApiKey)
//                .header("X-RapidAPI-Host", rapidApiHost)
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = null;
//        try {
//            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            throw new MarketConnectionException(e.getMessage());
//        }
//
//        String json = response.body();
//        ObjectMapper objectMapper = new ObjectMapper();
//        MarketStockQuoteRequest market = objectMapper.readValue(json, MarketStockQuoteRequest.class);
//        System.out.printf("Got %s quote: %s%n", symbol, market.price);
//        return market.price;
    }
}
