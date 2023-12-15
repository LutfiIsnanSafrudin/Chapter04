package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Keranjang;
import com.binaracademy.binarfud.repository.KeranjangRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class KeranjangServiceImpl implements KeranjangService {
    @Autowired
    private KeranjangRepository keranjangRepository;

    public Boolean addNewCart(Keranjang keranjang) {
            return Optional.ofNullable(keranjang)
                    .map(c -> {
                        Keranjang existingKeranjang = keranjangRepository.findByProductAndUser(c.getProduct(), c.getUser());
                        if (existingKeranjang != null) {
                            int newQuantity = existingKeranjang.getQuantity() + c.getQuantity();
                            existingKeranjang.setQuantity(newQuantity);
                            double newTotalPrice = existingKeranjang.getTotalPrice() + (c.getProduct().getPrice() * c.getQuantity());
                            existingKeranjang.setTotalPrice(newTotalPrice);
                            keranjangRepository.save(existingKeranjang);
                        } else {
                            c.setTotalPrice(c.getProduct().getPrice() * c.getQuantity());
                            keranjangRepository.save(c);
                        }
                        log.info("Keranjang {} successfully added", c.getId());
                        return true;
                    })
                    .orElseGet(() -> {
                        log.error("Failed to add new keranjang");
                        return false;
                    });
    }

    @Override
    public Boolean addNewKeranjang(Keranjang keranjang) {
        return null;
    }
}
