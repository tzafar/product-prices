package com.birlasoft.priceservice.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    public static Map<String, String> colorMapper = new HashMap<>();
    public static Map<String, String> currencyMapper = new HashMap<>();
    {
        colorMapper.put("white","ffffff");

        currencyMapper.put("GBP","£");
    }



}
