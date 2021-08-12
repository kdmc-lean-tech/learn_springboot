package com.demo.demo.Modules.Product.Controllers;

import com.demo.demo.Modules.Product.Models.Product;
import com.demo.demo.Modules.Product.Services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/product")
public record CreateProductUseCase(ProductService productService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody() Product product) {
        return this.productService.createProduct(product);
    }
}
