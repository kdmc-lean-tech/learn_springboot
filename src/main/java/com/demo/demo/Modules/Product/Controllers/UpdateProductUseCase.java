package com.demo.demo.Modules.Product.Controllers;

import com.demo.demo.Modules.Product.Models.Product;
import com.demo.demo.Modules.Product.Services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public record UpdateProductUseCase(ProductService productService) {

    @PutMapping(path = "{productId}")
    public Optional<Product> updateProductById(
            @RequestBody() Product product,
            @PathVariable("productId") Long productId
    ) {
        return this.productService.updateProduct(product, productId);
    }
}
