package com.stock.service;

import com.stock.entity.Product;
import com.stock.exception.ResourceNotFoundException;
import com.stock.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product updateProducts(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

       product.setName(productDetails.getName());
       product.setQuantityInStock(productDetails.getQuantityInStock());
       product.setPrice(productDetails.getPrice());

       Product updatedProduct = productRepository.save(product);
       return ResponseEntity.ok(updatedProduct).getBody();
    }

    public Map<String, Boolean> deleteProduct(Long id) {

        Product product = productRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("product not found for this id :: " + id));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public List<Product> findAll() {
        List<Product> list = productRepository.findAll();
        return list.stream()
                .sorted(Comparator.comparing(Product::getName))
                //.filter(x -> productRepository.existsById(x.getId()))
                //.filter(x -> x.getLocale().equals("de"))
                .collect(Collectors.toList());
    }
}
