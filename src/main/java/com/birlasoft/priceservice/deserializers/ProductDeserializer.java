package com.birlasoft.priceservice.deserializers;

import com.birlasoft.priceservice.config.Config;
import com.birlasoft.priceservice.domain.ColorSwatch;
import com.birlasoft.priceservice.domain.Price;
import com.birlasoft.priceservice.domain.Product;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.util.Arrays;

public class ProductDeserializer extends JsonDeserializer {

    @Override
    public Product deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = jp.getCodec().readTree(jp);
        Product product = new Product();
        product.setId(((TextNode)treeNode.get("productId")).textValue());
        product.setTitle(((TextNode)treeNode.get("title")).textValue());

        //colorSwatches
        String colorSwatches =  treeNode.get("colorSwatches").toString();
        ObjectMapper mapper = new ObjectMapper();
        ColorSwatch[] colorSwatchesArray = mapper.readValue(colorSwatches,ColorSwatch[].class);
        product.setColorSwatches(Arrays.asList(colorSwatchesArray));

        //nowPrice
        String currency = "";
        if(null != treeNode.get("price").get("currency")){
            currency = ((TextNode)treeNode.get("price").get("currency")).textValue();
            product.setCurrency(currency);
        }
        String priceString = treeNode.get("price").toString();
        if(null == treeNode.get("price").get("now").get("to")) {
            Price price = mapper.readValue(priceString, Price.class);
            product.setPrice(price);
            product.setNowPrice(Config.currencyMapper.get(currency) + price.getNow());
            if ((price.getNow() == Math.floor(price.getNow())) && !Double.isInfinite(price.getNow())) {
                if(price.getNow() > 10) {
                    product.setNowPrice(Config.currencyMapper.get(currency) + Math.round(price.getNow()));
                }
            }

            if(price.getWas() > 0.0 &&
                    price.getNow() > 0.0 ){
                product.setPriceReduction(Config.currencyMapper.get(currency) + (price.getWas() - price.getNow()));
            }
        }
        return product;
    }

    private double getDoubleValue(String input) {
        if(null == input) return 0.0;
        try{
            double doubleValue = Double.parseDouble(input);
            return doubleValue;
        } catch (Exception e){
            return 0.0;
        }

    }
}
