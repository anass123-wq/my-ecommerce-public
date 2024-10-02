package com.spring.productsevice.service;

import com.spring.productsevice.product.Product;
import com.spring.productsevice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product reduceStock(Long id, int quantity) {
        Product product = getProductById(id);
        if (product.getQuantity() < quantity) throw new IllegalArgumentException("Not enough stock");
        product.setQuantity(product.getQuantity() - quantity);
        return productRepository.save(product);
    }

    public Product addStock(Long id, int quantity) {
        Product product = getProductById(id);
        product.setQuantity(product.getQuantity() + quantity);
        return productRepository.save(product);
    }
}

