package com.spring.productsevice.service;

import com.spring.productsevice.product.Product;
import com.spring.productsevice.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
   public Product updateDate(Long id, Date date){
        Product product = getProductById(id);
        if (product == null) throw new IllegalArgumentException("Product not found with id: " + id);
        if (product.getDateCreating() == null) product.setDateCreating(date);
        else{ product.setDateCreating(product.getDateCreating());
            product.setDateLastModification(date);}
        return productRepository.save(product);
    }
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }


    // البحث عن المنتج بالاسم
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Product> findByCategoryContaining(String category){
        return productRepository.findByCategoryContaining(category);
    }
    // البحث عن المنتج بالـ ID
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" product not found with id: " + id));
    }

    // البحث عن المنتج بالباركود
    public Product findByBarcode(String barcode) {
        return productRepository.findByDescription(barcode);
    }

    // البحث عن المنتج بالسعر
    public List<Product> findByPrice(Double price) {
        return productRepository.findByPrice(price);
    }
    public List<Product> findByPriceInit(Double price) {
        return productRepository.findByPriceInit(price);
    }

    // البحث عن المنتج بالكمية
    public List<Product> findByQuantity(Integer quantity) {
        return productRepository.findByQuantity(quantity);
    }
    public Product updateProduct(long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPriceInit(product.getPriceInit());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage(product.getImage());
        existingProduct.setDateLastModification(new Date()); // Optional: update modification date
        return productRepository.save(existingProduct);
    }

    public List<Product> searchProducts(String query ) {
        Double price = null;
        Double priceInit= null;
        try {
            price = Double.parseDouble(query);
            priceInit = Double.parseDouble(query);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return productRepository.searchByNameOrDescriptionOrPrice(price,priceInit ,query);

    }
}

/*
    public Product reduceStock(String name, int quantity) {
        Product product = getProductByName(name);
        if (product.getQuantity() < quantity) throw new IllegalArgumentException("Not enough stock");
        product.setQuantity(product.getQuantity() - quantity);
        return productRepository.save(product);
    }

    public Product addStock(String name, int quantity) {
        Product product = getProductByName(name);
        product.setQuantity(product.getQuantity() + quantity);
        return productRepository.save(product);
    }
 public Product getProductByPrice(Long price) {
        return productRepository.findByPrice(price);
    }
    public Product getProductByQuantity(Long quantity) {
        return productRepository.findByQuantity(quantity);
    }
*/