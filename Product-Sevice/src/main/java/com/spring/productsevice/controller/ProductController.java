package com.spring.productsevice.controller;

import com.spring.productsevice.product.Product;
import com.spring.productsevice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PreAuthorize("hasAuthority('VIEW_PRODUCTS')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PreAuthorize("hasAuthority({'GET_ID' , 'ADMIN' })")
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) throws ChangeSetPersister.NotFoundException {
        return productService.getProductById(id);
    }
    @PreAuthorize("hasAuthority({'DELETE' , 'DELETE_PRODUCTS'}) ")
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}/reduceStock")
    public ResponseEntity<?> reduceStock(@PathVariable Long id, @RequestParam int quantity) {
        productService.reduceStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/addStock")
    public ResponseEntity<?> addStock(@PathVariable("id") Long id, @RequestParam int quantity) {
        productService.addStock(id, quantity);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/updateDate")
    public ResponseEntity<?> updateDate(@PathVariable("id") Long id, @RequestParam("date") Date date){
        productService.updateDate(id,date);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search/name")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.findByNameContaining(name);
    }
    @GetMapping("/search/category")
    public List<Product> searchByCategory(@RequestParam String category) {
        return productService.findByCategoryContaining(category);
    }
    @GetMapping("/search/id")
    public ResponseEntity<Product> searchById(@RequestParam Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/barcode")
    public Product searchByBarcode(@RequestParam String barcode) {
        return productService.findByBarcode(barcode);
    }

    @GetMapping("/search/price")
    public List<Product> searchByPrice(@RequestParam Double price) {
        return productService.findByPrice(price);
    }
    @GetMapping("/search/priceInit")
    public List<Product> searchByPriceInit(@RequestParam Double priceInit) {
        return productService.findByPriceInit(priceInit);
    }

    @GetMapping("/search/quantity")
    public List<Product> searchByQuantity(@RequestParam Integer quantity) {
        return productService.findByQuantity(quantity);
    }

    @PreAuthorize("hasAuthority({'UPDATE' }) ")
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAuthority({'SEARCH' }) ")
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> results = productService.searchProducts(query);
        return ResponseEntity.ok(results
        );
    }
}