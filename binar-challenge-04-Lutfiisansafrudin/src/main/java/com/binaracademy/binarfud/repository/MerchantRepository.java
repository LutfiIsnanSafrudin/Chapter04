package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Merchant findByMerchantName(String name);
    Page<Merchant> findByOpen(Boolean open, Pageable pageable);
}
