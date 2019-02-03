package com.birlasoft.priceservice.deserializers;

import com.birlasoft.priceservice.config.Config;
import com.birlasoft.priceservice.domain.ColorSwatch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ColorSwatchDeserializer extends JsonDeserializer {

    @Override
    public ColorSwatch deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = jp.getCodec().readTree(jp);
        ColorSwatch colorSwatch = new ColorSwatch();
        colorSwatch.setColor(treeNode.get("color").toString());
        colorSwatch.setSkuId(treeNode.get("skuId").toString());
        colorSwatch.setRgbColor(Config.colorMapper.get("white"));
        return colorSwatch;
    }

}
