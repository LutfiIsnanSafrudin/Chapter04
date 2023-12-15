package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Merchant;
import com.binaracademy.binarfud.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService{
    @Autowired
    private MerchantRepository merchantRepository;

    public Boolean addNewMerchant(Merchant merchant) {
        return Optional.ofNullable(merchant)
                .map(newMerchant -> merchantRepository.save(merchant))
                .map(result-> {
                    boolean isSuccess = Objects.nonNull(result);
                    if(isSuccess) {
                        log.info("Merchant {} successfully added", result.getMerchantName());
                    }
                    return isSuccess;
                })
                .orElseGet(() -> {
                    log.error("Failed to add new merchant");
                    return false;
                });
    }

    public Boolean updateStatusMerchant(String merchantName) {
        return Optional.ofNullable(merchantName)
                .map(merchantRepository::findByMerchantName)
                .map(merchant1 -> {
                    merchant1.setOpen(merchant1.getOpen() ? false : true);
                    log.info("Merchant {} successfully updated", merchant1.getMerchantName());
                    return merchantRepository.save(merchant1);
                })
                .map(Objects::nonNull)
                .orElseGet(() -> {
                    log.error("Failed to update merchant");
                    return false;
                });
    }

    public Page<Merchant> getAllOpenMerchant(Boolean open, Pageable pageable) {
        return merchantRepository.findByOpen(open, pageable);
    }
}
