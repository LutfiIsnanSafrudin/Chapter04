package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    Product findByProductName(String productName);
//    Product deleteByProductName(String productName);
    Page<Product> findAll(Pageable pageable);
}
