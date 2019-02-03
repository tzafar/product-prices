package com.birlasoft.priceservice.controllers;

import com.birlasoft.priceservice.domain.Product;
import com.birlasoft.priceservice.services.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class PriceController {

    @Autowired
    ProductService productService;


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Ger price for products")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Products"),@ApiResponse(code = 400 , message = "no products found")})
    @ResponseBody
    @GetMapping("/prices")
    public ResponseEntity getProducts(@RequestParam (required = false ) String priceLabel) throws IOException {
        ResponseEntity responseEntity = null;
        Optional<List<Product>> products = productService.getProductPrices(priceLabel);
        if(products.isPresent()) {
            if (products.get().size() > 0) {
                responseEntity = ResponseEntity.ok(products.get());
            }
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;

    }
}
