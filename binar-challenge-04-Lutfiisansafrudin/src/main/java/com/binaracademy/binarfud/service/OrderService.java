package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Boolean addNewOrder(Order order);
    Page<Order> getAllOrderWithPagination(Pageable pageable);
}
