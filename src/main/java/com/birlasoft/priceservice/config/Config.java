package com.birlasoft.priceservice.config;

import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    public static Map<String, String> colorMapper = new HashMap<>();
    public static Map<String, String> currencyMapper = new HashMap<>();
    {
        //for simplicity, and time saving just keeping one color and currency in the Map.
        //using these two universally for all products.
        colorMapper.put("white","ffffff");
        currencyMapper.put("GBP","£");
    }



}
