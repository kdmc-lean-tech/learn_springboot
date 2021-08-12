package com.demo.demo.Modules.Product.Controllers;

import com.demo.demo.Modules.Product.Models.Product;
import com.demo.demo.Modules.Product.Services.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public record DeleteProductUseCase(ProductService productService) {

    @DeleteMapping(path = "{productId}")
    public Optional<Product> deleteProductById(@PathVariable("productId") Long productId) {
        return this.productService.deleteProduct(productId);
    }
}
