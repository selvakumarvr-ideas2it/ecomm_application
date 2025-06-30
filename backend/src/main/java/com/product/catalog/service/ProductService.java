package com.product.catalog.service;

import com.product.catalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final CsvService csvService;

    @Autowired
    public ProductService(CsvService csvService) {
        this.csvService = csvService;
    }

    public List<Product> getAllProducts() {
        return csvService.readProductsFromCsv();
    }

    public Product createProduct(Product product) {
        List<Product> products = getAllProducts();
        Long newId = products.stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0) + 1;
        
        product.setId(newId);
        products.add(product);
        csvService.writeProductsToCsv(products);
        return product;
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        List<Product> products = getAllProducts();
        boolean productExists = false;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                updatedProduct.setId(id);
                products.set(i, updatedProduct);
                productExists = true;
                break;
            }
        }

        if (productExists) {
            csvService.writeProductsToCsv(products);
            return Optional.of(updatedProduct);
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        List<Product> products = getAllProducts();
        boolean removed = products.removeIf(product -> product.getId().equals(id));
        
        if (removed) {
            csvService.writeProductsToCsv(products);
        }
        return removed;
    }
} 