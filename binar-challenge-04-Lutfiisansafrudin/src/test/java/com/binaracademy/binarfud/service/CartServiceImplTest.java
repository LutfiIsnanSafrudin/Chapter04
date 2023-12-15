package com.binaracademy.binarfud.service;

import static org.junit.jupiter.api.Assertions.*;

import com.binaracademy.binarfud.model.Keranjang;
import com.binaracademy.binarfud.model.Merchant;
import com.binaracademy.binarfud.model.Product;
import com.binaracademy.binarfud.model.User;
import com.binaracademy.binarfud.repository.KeranjangRepository;
import com.binaracademy.binarfud.repository.MerchantRepository;
import com.binaracademy.binarfud.repository.ProductRepository;
import com.binaracademy.binarfud.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartServiceImplTest {

    @Autowired
    private KeranjangRepository keranjangRepository;

    @Autowired
    private KeranjangServiceImpl cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .username("SampleUser")
                .password("SamplePassword")
                .email("sample@mail.com")
                .build();
        userRepository.save(user);

        Merchant merchant = Merchant.builder()
                .merchantName("SampleMerchant")
                .merchantLocation("SampleLocation")
                .open(true)
                .build();
        merchantRepository.save(merchant);

        Product product = Product.builder().
                productName("SampleProduct").
                price(100.0).
                merchant(merchant).
                build();
        productRepository.save(product);
    }

    @AfterEach
    public void tearDown() {
        keranjangRepository.deleteAll();
        productRepository.deleteAll();
        merchantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAddNewCart() {
        Product product = productRepository.findByProductName("SampleProduct");
        User user = userRepository.findByUsername("SampleUser");
        Keranjang keranjang = Keranjang.builder().product(product).quantity(1).user(user).build();
        boolean result = cartService.addNewCart(keranjang);
        assertTrue(result);
    }

    @Test
    public void testExitingProductCart() {
        Product product = productRepository.findByProductName("SampleProduct");
        User user = userRepository.findByUsername("SampleUser");
        Keranjang keranjang = Keranjang.builder().product(product).quantity(1).user(user).build();
        cartService.addNewCart(keranjang);
        boolean result = cartService.addNewCart(keranjang);
        int expected = 2;
        assertTrue(result);
        assertEquals(expected, keranjangRepository.findByProductAndUser(product, user).getQuantity());
    }
}

