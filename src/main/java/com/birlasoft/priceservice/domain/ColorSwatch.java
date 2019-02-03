package com.birlasoft.priceservice.domain;
import com.birlasoft.priceservice.config.Config;
import com.birlasoft.priceservice.deserializers.ColorSwatchDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ColorSwatchDeserializer.class)
public class ColorSwatch {

    private String color;
    private String rgbColor;
    private String skuId;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRgbColor() {
        return rgbColor;
    }

    public void setRgbColor(String rgbColor) {
        this.rgbColor = Config.colorMapper.get("white");
    }

    public String getSkuId() {
        return this.skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
