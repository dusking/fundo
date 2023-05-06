package com.fundo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundo.exception.MarketConnectionException;
import com.fundo.exception.invalidMarketRequestException;
import com.fundo.requests.MarketStockQuoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class ExchangeService {
    private final String rapidMarketApiHost;

    private final String rapidMarketApiEndpoint;

    private final String rapidMarketApiKey;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final static Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    @Autowired
    public ExchangeService(@Value("${rapid.marketApi.host}") String rapidMarketApiHost,
                           @Value("${rapid.marketApi.endpoint}") String rapidMarketApiEndpoint,
                           @Value("${rapid.marketApi.key}") String rapidMarketApiKey) {
        this.rapidMarketApiHost = rapidMarketApiHost;
        this.rapidMarketApiEndpoint = rapidMarketApiEndpoint;
        this.rapidMarketApiKey = rapidMarketApiKey;
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
//        return 10;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rapidMarketApiEndpoint + "?symbol=" + symbol + "&format=json&outputsize=30"))
                .header("X-RapidAPI-Key", rapidMarketApiKey)
                .header("X-RapidAPI-Host", rapidMarketApiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new MarketConnectionException(e.getMessage());
        }

        String json = response.body();
        MarketStockQuoteResponse market = objectMapper.readValue(json, MarketStockQuoteResponse.class);
        logger.info(String.format("Got %s quote: %s%n", symbol, market.price));
        return market.price;
    }
}
