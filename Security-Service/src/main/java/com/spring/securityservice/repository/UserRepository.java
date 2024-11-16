package com.spring.securityservice.repository;

import com.spring.securityservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.email LIKE CONCAT('%', :query, '%') OR r.name LIKE CONCAT('%', :query, '%')")
    List<User> findByUsernameContainingOrRoles_NameContaining(@Param("query") String query);

}