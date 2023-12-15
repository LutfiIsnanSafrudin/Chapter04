package com.binaracademy.binarfud.service;

import static org.junit.jupiter.api.Assertions.*;

import com.binaracademy.binarfud.model.Merchant;
import com.binaracademy.binarfud.model.Product;
import com.binaracademy.binarfud.repository.MerchantRepository;
import com.binaracademy.binarfud.repository.ProductRepository;
import com.binaracademy.binarfud.response.ProductResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private MerchantRepository merchantRepository;

    @BeforeEach
    public void setUp() {
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
        productRepository.deleteAll();
        merchantRepository.deleteAll();
    }


    @Test
    public void testAddNewProduct() {
        Merchant merchant = merchantRepository.findByMerchantName("SampleMerchant");
        Product product = Product.builder().
                productName("CreateProduct").
                price(100.0).
                merchant(merchant).
                build();
        boolean result = productService.addNewProduct(product);
        assertTrue(result);
    }

    @Test
    public void testUpdateProduct() {
        Merchant merchant = merchantRepository.findByMerchantName("SampleMerchant");

        Product product = Product.builder().
                productName("SampleProductUpdate").
                price(200.0).
                merchant(merchant).
                build();
        boolean result = productService.updateProduct("SampleProduct", product);
        assertTrue(result);
    }

    @Test
    public void testDeleteProduct() {
        boolean result = productService.deleteProduct("SampleProduct");
        assertTrue(result);
    }

    @Test
    public void testGetProductDetail() {
        Merchant merchant = merchantRepository.findByMerchantName("SampleMerchant");
        ProductResponse response = productService.getProductDetail("SampleProduct");
        assertNotNull(response);
        assertEquals("SampleProduct", response.getProductName());
        assertEquals(merchant.getMerchantCode(), response.getMerchant().getMerchantCode());
        assertEquals(100.0, response.getPrice());
    }

    @Test
    public void testGetProductsWithPagination(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productService.getProductsWithPagination(pageable);
        assertNotNull(products);
        assertEquals(1, products.getTotalElements());
        assertEquals(1, products.getTotalPages());
        assertEquals("SampleProduct", products.getContent().get(0).getProductName());
    }
}
