package com.birlasoft.priceservice.controllers;

import com.birlasoft.priceservice.config.Config;
import com.birlasoft.priceservice.domain.Product;
import com.birlasoft.priceservice.domain.ProductResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PriceController {

    @Value("${price.endpoint}")
    private String priceEndpoint;

    @GetMapping("/items")
    public List<Product> getProducts(@RequestParam (required = false ) String priceLabel) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(priceEndpoint,String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        ProductResponse productResponse = mapper.readValue(responseEntity.getBody(), ProductResponse.class);
        List<Product> products = productResponse.getProducts().stream().map(p -> {
            if(null != p.getPrice() && p.getPrice().getWas() > 0.0 && p.getPrice().getNow() > 0.0){
                String currencySymbol = Config.currencyMapper.get(p.getCurrency());
                String label = "Was " + currencySymbol + p.getPrice().getWas() + " Now " + currencySymbol + p.getPrice().getNow();
                p.setPriceLabel(label);
            } else {
                p.setPriceLabel("");
            }
            return p;
        }).collect(Collectors.toList());
        return products;
    }
}
