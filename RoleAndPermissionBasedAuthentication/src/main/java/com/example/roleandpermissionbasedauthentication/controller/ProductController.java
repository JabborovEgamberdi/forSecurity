package com.example.roleandpermissionbasedauthentication.controller;

import com.example.roleandpermissionbasedauthentication.entity.Product;
import com.example.roleandpermissionbasedauthentication.repository.ProductRepository;
import com.example.roleandpermissionbasedauthentication.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //MANAGER, ADMIN
//    @PreAuthorize(value = "hasAnyRole('MANAGER', 'DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    //DIRECTOR
//    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @PostMapping("/add")
    public HttpEntity<?> addProduct(@RequestBody Product product) {
        return ResponseHandler.generateResponse("success",
                HttpStatus.CREATED,
                productRepository.save(product),
                true);
    }

    //DIRECTOR
//    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product editingProduct = optionalProduct.get();
            editingProduct.setName(product.getName());
            Product save = productRepository.save(editingProduct);

            return ResponseHandler.generateResponse(
                    "success",
                    HttpStatus.OK,
                    save,
                    true);
        }
        return ResponseEntity.notFound().build();
    }


    //DIRECTOR, MANAGER
//    @PreAuthorize(value = "hasAnyRole('MANAGER', 'DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //USER, MANAGER, DIRECTOR
//    @PreAuthorize(value = "hasAnyRole('USER', 'MANAGER', 'DIRECTOR')")
    @PreAuthorize(value = "hasAnyAuthority('GET_ONE_PRODUCT', 'GET_ALL_PRODUCT', 'DELETE_PRODUCT')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity
                .status(optionalProduct.isPresent() ? 200 : 400)
                .body(optionalProduct.orElse(null));
    }

}
