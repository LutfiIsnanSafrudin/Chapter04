package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.model.Keranjang;
import com.binaracademy.binarfud.model.Product;
import com.binaracademy.binarfud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeranjangRepository extends JpaRepository<Keranjang, String> {
    Keranjang findByProductAndUser(Product product, User user);
    List<Keranjang> findByUser(User user);
}
