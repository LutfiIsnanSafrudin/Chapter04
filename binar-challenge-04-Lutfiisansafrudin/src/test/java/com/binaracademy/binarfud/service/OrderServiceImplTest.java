package com.binaracademy.binarfud.service;

import static org.junit.jupiter.api.Assertions.*;

import com.binaracademy.binarfud.model.*;
import com.binaracademy.binarfud.repository.*;
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
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private KeranjangRepository keranjangRepository;

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

        Keranjang keranjang = Keranjang.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .totalPrice(product.getPrice() * 1)
                .build();
        keranjangRepository.save(keranjang);
    }

    @AfterEach
    public void tearDown() {
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
        keranjangRepository.deleteAll();
        productRepository.deleteAll();
        merchantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAddNewOrder() {
        User user = userRepository.findByUsername("SampleUser");
        Order order = Order.builder()
                .user(user)
                .destinationAddress("SampleAddress")
                .note("SampleNote")
                .build();
        boolean result = orderService.addNewOrder(order);
        assertTrue(result);
    }

    @Test
    public void testGetAllOrderWithPagination() {
        User user = userRepository.findByUsername("SampleUser");
        Order order = Order.builder()
                .user(user)
                .destinationAddress("SampleAddress")
                .note("SampleNote")
                .build();
        orderService.addNewOrder(order);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orderPagination = orderService.getAllOrderWithPagination(pageable);
        assertNotNull(orderPagination);
        assertEquals(1, orderPagination.getTotalElements());
        assertEquals(1, orderPagination.getTotalPages());
        assertEquals("SampleAddress", orderPagination.getContent().get(0).getDestinationAddress());
    }
}
