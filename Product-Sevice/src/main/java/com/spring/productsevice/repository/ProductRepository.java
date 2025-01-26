package com.spring.productsevice.repository;

import com.spring.productsevice.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     Product findByDescription(String description);

     @Query("SELECT p FROM Product p WHERE p.name = :name")
     List<Product> findByNameContainingIgnoreCase(@Param("name")String name);
     @Query("SELECT p FROM Product p WHERE p.category = :name")
     List<Product> findByCategoryContaining(@Param("name")String category);

     Product findByName(String name);

     List<Product> findByPrice(Double price);

     List<Product> findByQuantity(Integer quantity);
     List<Product> findByPriceInit(Double price);

     @Query("SELECT p FROM Product p WHERE p.name LIKE %:query% OR p.description LIKE %:query% OR p.price >= :amount OR p.category LIKE %:query% OR p.priceInit >= :amountInit ")
     List<Product> searchByNameOrDescriptionOrPrice(@Param("amount") Double amount,@Param("amountInit") Double amountInit, @Param("query") String query);
}
