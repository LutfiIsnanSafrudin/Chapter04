package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MerchantService {
    Boolean addNewMerchant(Merchant merchant);
    Boolean updateStatusMerchant(String merchantName);
    Page<Merchant> getAllOpenMerchant(Boolean open, Pageable pageable);
}
