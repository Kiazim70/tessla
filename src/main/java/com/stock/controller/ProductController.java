package com.stock.controller;


import com.stock.entity.Product;
import com.stock.exception.AppException;
import com.stock.exception.ResourceNotFoundException;
import com.stock.repository.ProductRepository;
import com.stock.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;


    @PostMapping("/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) throws AppException {
        Optional<Product> productOptional = productService.getProductById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return ResponseEntity.ok(product);
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {

        Product updatedProduct = productService.updateProducts(id, productDetails);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        return productService.deleteProduct(id);
    }
}
