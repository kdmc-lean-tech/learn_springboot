package com.demo.demo.Modules.Product.Services;

import com.demo.demo.Modules.Product.Enums.Category;
import com.demo.demo.Modules.Product.Enums.SortProducts;
import com.demo.demo.Modules.Product.Models.Product;
import com.demo.demo.Modules.Product.Repositories.ProductRepository;
import com.demo.demo.Shared.Enums.Sorting;
import com.demo.demo.Shared.Helpers.Paginate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        try {
            return this.productRepository.save(product);
        } catch (Exception e) {
            throw new IllegalStateException("Internal Server Error");
        }
    }

    public Optional<Product> getProductById(Long productId) {
        boolean productExist = this.productRepository.existsById(productId);
        if (!productExist) {
            throw new IllegalStateException("The product with id" + productId + " no exits.");
        }
        return this.productRepository.findById(productId);
    }

    public List<Product> getProducts(Paginate paginate) {
        return this.productRepository.findAll().stream()
                                            .sorted(this.sortProducts(
                                                    (SortProducts) paginate.getSort(),
                                                    paginate.getSorting()))
                                            .skip(paginate.getOffset())
                                            .limit(paginate.getPageSize())
                                            .collect(Collectors.toList());
    }

    public Optional<Product> deleteProduct(Long productId) {
        boolean productExist = this.productRepository.existsById(productId);
        if (!productExist) {
            throw new IllegalStateException("The product with id" + productId + " no exits.");
        }
        Optional<Product> product = this.getProductById(productId);
        this.productRepository.deleteById(productId);
        return product;
    }

    public Optional<Product> updateProduct(Product product, Long productId) {
        boolean productExist = this.productRepository.existsById(productId);
        if (!productExist) {
            throw new IllegalStateException("The product with id" + productId + " no exits.");
        }
        return this.productRepository.findById(productId)
                .map(model -> {
                    model.setName(product.getName());
                    model.setPrice(product.getPrice());
                    model.setCategory(product.getCategory());
                    model.setDescription(product.getDescription());
                    return model;
                });
    }

    public Map<Category, List<Product>> groupingProductsByCategory() {
        return this.productRepository.findAll().stream()
                                        .collect(Collectors.groupingBy(Product::getCategory));
    }

    public Map<Category, Double> getAverageProductsByCategories() {
        return this.productRepository.findAll().stream()
                                            .collect(
                                                    Collectors.groupingBy(
                                                        Product::getCategory,
                                                        Collectors.averagingDouble(Product::getPrice)));
    }

    public List<Product> getProductsByRangePrices(double minPrice, double maxPrice, Paginate paginate) {
        return this.productRepository.findAll().stream()
                                        .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice ? true : false)
                                        .sorted(this.sortProducts(
                                                (SortProducts) paginate.getSort(), paginate.getSorting()))
                                        .skip(paginate.getOffset())
                                        .limit(paginate.getPageSize())
                                        .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategory(Category category, Paginate paginate) {
        return this.productRepository.findAll().stream()
                                            .filter(k -> k.getCategory() == category)
                                            .sorted(this.sortProducts(
                                                (SortProducts) paginate.getSort(), paginate.getSorting()))
                                            .skip(paginate.getOffset())
                                            .limit(paginate.getPageSize())
                                            .collect(Collectors.toList());
    }

    private Comparator<Product> sortProducts(SortProducts sortProducts, Sorting sorting) {
       Comparator<Product> comparator = null;
       switch (sortProducts) {
           case NAME: {
               if (sorting == Sorting.ASC) {
                   comparator = Comparator.comparing(Product::getName);
               } else if (sorting == Sorting.DESC) {
                    comparator = (pa, pb) -> pb.getName().compareTo(pa.getName());
               }
               break;
           }
           case PRICE: {
               if (sorting == Sorting.ASC) {
                   comparator = Comparator.comparingDouble(Product::getPrice);
               } else if (sorting == Sorting.DESC) {
                   comparator = (pa, pb) -> Double.compare(pb.getPrice(), pa.getPrice());
               }
               break;
           }
           case CATEGORY: {
               if (sorting == Sorting.ASC) {
                   comparator = Comparator.comparing(Product::getCategory);
               } else {
                   comparator = (pa, pb) -> pb.getCategory().compareTo(pa.getCategory());
               }
               break;
           }
           default: {
               return Comparator.comparing(Product::getName);
           }
       }
        return comparator;
    }
}
