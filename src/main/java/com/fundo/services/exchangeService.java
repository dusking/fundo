package com.fundo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundo.config.MarketClientConfig;
import com.fundo.exception.MarketConnectionException;
import com.fundo.exception.invalidMarketRequestException;
import com.fundo.requests.MarketStockQuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class exchangeService {

    private MarketClientConfig marketClientConfig;

    private String marketApiHost;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public exchangeService(MarketClientConfig marketClientConfig, @Value("${market.api.host}") String marketApiHost) {
        this.marketClientConfig = marketClientConfig;
        this.marketApiHost = marketApiHost;
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
        marketClientConfig.setEnv();
        System.out.printf("marketClientInfo : %s", marketClientConfig.host);

//        return 10;
        String rapidApiHost = marketApiHost;
        String RapidApiEndpoint = marketClientConfig.endpoint;
        String rapidApiKey = marketClientConfig.apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RapidApiEndpoint + "?symbol=" + symbol + "&format=json&outputsize=30"))
                .header("X-RapidAPI-Key", rapidApiKey)
                .header("X-RapidAPI-Host", rapidApiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new MarketConnectionException(e.getMessage());
        }

        String json = response.body();
        MarketStockQuoteResponse market = objectMapper.readValue(json, MarketStockQuoteResponse.class);
        System.out.printf("Got %s quote: %s%n", symbol, market.price);
        return market.price;
    }
}
