package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Keranjang;
import org.springframework.stereotype.Service;

@Service
public interface KeranjangService {
    Boolean addNewKeranjang(Keranjang keranjang);
}
