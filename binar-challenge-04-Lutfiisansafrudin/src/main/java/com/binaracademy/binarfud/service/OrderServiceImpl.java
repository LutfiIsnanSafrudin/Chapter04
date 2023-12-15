package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Order;
import com.binaracademy.binarfud.model.OrderDetail;
import com.binaracademy.binarfud.repository.KeranjangRepository;
import com.binaracademy.binarfud.repository.OrderDetailRepository;
import com.binaracademy.binarfud.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KeranjangRepository keranjangRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Boolean addNewOrder(Order order) {
        return Optional.ofNullable(order)
                .map(user -> keranjangRepository.findByUser(order.getUser()))
                .map(carts -> {
                    order.setCompleted(false);
                    order.setOrderTime(new Date());
                    Order newOrder = orderRepository.save(order);
                    carts.forEach(cart -> {
                        OrderDetail orderDetail = OrderDetail.builder()
                                .order(newOrder)
                                .product(cart.getProduct())
                                .quantity(cart.getQuantity())
                                .totalPrice(cart.getTotalPrice())
                                .build();
                        orderDetailRepository.save(orderDetail);
                    });
                    keranjangRepository.deleteAll(carts);
                    log.info("Order {} successfully added", order.getId());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Page<Order> getAllOrderWithPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

}
