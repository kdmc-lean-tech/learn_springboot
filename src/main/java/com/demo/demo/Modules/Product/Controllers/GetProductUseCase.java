package com.demo.demo.Modules.Product.Controllers;

import com.demo.demo.Modules.Product.Models.Product;
import com.demo.demo.Modules.Product.Services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public record GetProductUseCase(ProductService productService) {

    @GetMapping(path = "{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Product> getProductById(@PathVariable("productId") Long productId) {
        return this.productService.getProductById(productId);
    }
}
