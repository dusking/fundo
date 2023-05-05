package com.fundo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Configuration
public class MarketClientConfig {

    @Autowired
    private Environment env;

    public String host;
    public String endpoint;
    public String apiKey;

    public void setEnv() {
        this.host = env.getProperty("market.api.host");
        this.endpoint = env.getProperty("market.api.endpoint");
        this.apiKey = env.getProperty("market.api.key");
    }
}
