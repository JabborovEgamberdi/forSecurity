package com.example.roleandpermissionbasedauthentication.repository;

import com.example.roleandpermissionbasedauthentication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}