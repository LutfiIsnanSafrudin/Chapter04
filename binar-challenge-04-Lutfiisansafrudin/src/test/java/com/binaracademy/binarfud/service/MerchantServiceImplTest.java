package com.binaracademy.binarfud.service;

import static org.junit.jupiter.api.Assertions.*;

import com.binaracademy.binarfud.model.Merchant;
import com.binaracademy.binarfud.repository.MerchantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MerchantServiceImplTest {
    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantServiceImpl merchantService;

    @BeforeEach
    public void setUp() {
        Merchant merchant = Merchant.builder()
                .merchantName("SampleMerchant")
                .merchantLocation("SampleLocation")
                .open(true)
                .build();
        merchantRepository.save(merchant);
    }
    @AfterEach
    public void tearDown() {
        merchantRepository.deleteAll();
    }

    @Test
    public void testAddNewMerchant() {
        Merchant merchant = Merchant.builder()
                .merchantName("SampleMerchantCreate")
                .merchantLocation("SampleLocationCreate")
                .open(true)
                .build();
        boolean result = merchantService.addNewMerchant(merchant);
        assertTrue(result);
    }

    @Test
    public void testUpdateStatusMerchant() {
        boolean result = merchantService.updateStatusMerchant("SampleMerchant");
        assertTrue(result);
    }

    @Test
    public void testGetAllOpenMerchant() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Merchant> merchant = merchantService.getAllOpenMerchant(true, pageable);
        assertNotNull(merchant);
        assertEquals(1, merchant.getTotalElements());
        assertEquals(1, merchant.getTotalPages());
        assertEquals("SampleMerchant", merchant.getContent().get(0).getMerchantName());
    }
}
