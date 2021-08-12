package com.demo.demo.Modules.Product.Controllers;

import com.demo.demo.Modules.Product.Enums.Category;
import com.demo.demo.Modules.Product.Enums.SortProducts;
import com.demo.demo.Modules.Product.Models.Product;
import com.demo.demo.Modules.Product.Services.ProductService;
import com.demo.demo.Shared.Enums.Sorting;
import com.demo.demo.Shared.Helpers.Paginate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/product")
public record GetProductsUseCase(ProductService productService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sort") SortProducts sort,
            @RequestParam("sorting") Sorting sorting
            ) {
        return this.productService.getProducts(new Paginate<SortProducts>(page, pageSize, sorting, sort));
    }

    @GetMapping(path = "/categories")
    @ResponseStatus(HttpStatus.OK)
    public Map<Category, List<Product>> getProductsByCat() {
        return this.productService.groupingProductsByCategory();
    }

    @GetMapping(path = "/average")
    @ResponseStatus(HttpStatus.OK)
    public Map<Category, Double> getAverageProductsByCategories() {
        return this.productService.getAverageProductsByCategories();
    }

    @GetMapping(path = "/range/prices")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByRangePrices(
            @RequestParam("minPrice") Long minPrice,
            @RequestParam("maxPrice") Long maxPrice,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sort") SortProducts sort,
            @RequestParam("sorting") Sorting sorting
    ) {
        return this.productService.getProductsByRangePrices(
                minPrice,
                maxPrice,
                new Paginate<SortProducts>(page, pageSize, sorting, sort)
        );
    }

    @GetMapping(path = "/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByCategory(
            @PathVariable("category") Category category,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sort") SortProducts sort,
            @RequestParam("sorting") Sorting sorting
    ) {
        return this.productService.getProductsByCategory(
                category,
                new Paginate<SortProducts>(page, pageSize, sorting, sort)
        );
    }
}
