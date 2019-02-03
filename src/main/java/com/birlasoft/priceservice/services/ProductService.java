package com.birlasoft.priceservice.services;

import com.birlasoft.priceservice.config.Config;
import com.birlasoft.priceservice.domain.Product;
import com.birlasoft.priceservice.domain.ProductResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${price.endpoint}")
    private String priceEndpoint;

    public Optional<List<Product>> getProductPrices(String priceLabel){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(priceEndpoint,String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        ProductResponse productResponse;
        try {
            productResponse = mapper.readValue(responseEntity.getBody(), ProductResponse.class);
        } catch (IOException e){
            logger.error("error " + e);
            return empty();
        }

        if(null == productResponse.getProducts()) return empty();
        return includePriceLabel(productResponse.getProducts(),priceLabel);
    }

    private Optional<List<Product>> includePriceLabel(List<Product> productsInput, String priceLabel){
        if(null == priceLabel) priceLabel = "ShowWasNow";
        List<Product> products =  null;
        switch (priceLabel){
            case "ShowWasNow":
                products = productsInput.stream().map(this::includeShowWasNow).collect(Collectors.toList());
                break;
            case "ShowWasThenNow":
                products = productsInput.stream().map(this::includeShowWasThenNow).collect(Collectors.toList());
            case "ShowPercDscount":
                products = productsInput.stream().map(this::includeShowPercDscount).collect(Collectors.toList());
                break;
        }
        return Optional.of(products);
    }

    private  Product includeShowPercDscount(Product product) {
        if(null != product.getPrice() && product.getPrice().getWas() > 0.0 && product.getPrice().getNow() > 0.0){
            double percentage = ( product.getPrice().getNow() / product.getPrice().getWas() ) * 100;
            String currencySymbol = Config.currencyMapper.get(product.getCurrency());
            String label = percentage+ "% off - Now " + currencySymbol + product.getPrice().getNow();
            product.setPriceLabel(label);
        } else {
            product.setPriceLabel("");
        }
        return product;
    }

    private Product includeShowWasThenNow(Product product) {
        if(null != product.getPrice() && product.getPrice().getWas() > 0.0 && product.getPrice().getNow() > 0.0){
            String currencySymbol = Config.currencyMapper.get(product.getCurrency());
            String label = "Was " + currencySymbol;
            if(product.getPrice().getThen2() > 0.0){
                label += " then " + currencySymbol + product.getPrice().getThen2();
            } else if (product.getPrice().getThen1() > 0.0){
                label += " then " + currencySymbol + product.getPrice().getThen1();
            }
            label+= product.getPrice().getWas() + " Now " + currencySymbol + product.getPrice().getNow();
            product.setPriceLabel(label);
        } else {
            product.setPriceLabel("");
        }
        return product;
    }

    private Product includeShowWasNow(Product product){
        if(null != product.getPrice() && product.getPrice().getWas() > 0.0 && product.getPrice().getNow() > 0.0){
            String currencySymbol = Config.currencyMapper.get(product.getCurrency());
            String label = "Was " + currencySymbol + product.getPrice().getWas() + " Now " + currencySymbol + product.getPrice().getNow();
            product.setPriceLabel(label);
        } else {
            product.setPriceLabel("");
        }
        return product;
    }
}
