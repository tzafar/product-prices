package com.birlasoft.priceservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
