package com.birlasoft.priceservice.controllers;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class PriceController {

    @Value("${price.endpoint}")
    private String priceEndpoint;

    @GetMapping("/items")
    public List<Product> getProducts() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(priceEndpoint,String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        ProductResponse productResponse = mapper.readValue(responseEntity.getBody(), ProductResponse.class);
        return productResponse.getProducts();
    }
}
