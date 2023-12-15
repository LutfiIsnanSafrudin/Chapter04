package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Product;
import com.binaracademy.binarfud.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Boolean addNewProduct(Product product);
    Boolean updateProduct(String productName, Product product);
    Boolean deleteProduct(String productName);
    ProductResponse getProductDetail(String selectedProductName);
    Page<Product> getProductsWithPagination(Pageable pageable);
}
