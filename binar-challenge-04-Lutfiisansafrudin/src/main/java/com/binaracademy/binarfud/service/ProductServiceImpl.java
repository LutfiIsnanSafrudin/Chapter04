package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.Product;
import com.binaracademy.binarfud.repository.ProductRepository;
import com.binaracademy.binarfud.response.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Boolean addNewProduct(Product product) {
        return Optional.ofNullable(product)
                .map(newProduct -> productRepository.save(product))
                .map(result-> {
                    boolean isSuccess = Objects.nonNull(result);
                    if(isSuccess) {
                        log.info("Product {} successfully added", result.getProductName());
                    }
                    return isSuccess;
                })
                .orElseGet(() -> {
                    log.error("Failed to add new product");
                    return false;
                });
    }
    @Override
    public Boolean updateProduct(String productName, Product product) {
        return Optional.ofNullable(productName)
                .map(productRepository::findByProductName)
                .map(existingProduct -> {
                    existingProduct.setProductName(product.getProductName());
                    existingProduct.setMerchant(product.getMerchant());
                    existingProduct.setPrice(product.getPrice());
                    productRepository.save(existingProduct);
                    log.info("Product {} successfully updated", existingProduct.getProductName());
                    return true;
                })
                .orElseGet(() -> {
                    log.error("Failed to update product");
                    return false;
                });
    }
    @Override
    public Boolean deleteProduct(String productName) {
        return Optional.ofNullable(productName)
                .map(productRepository::findByProductName)
                .map(existingProduct -> {
                    productRepository.delete(existingProduct);
                    log.info("Product {} successfully deleted", existingProduct.getProductName());
                    return true;
                })
                .orElseGet(() -> {
                    log.error("Failed to delete product");
                    return false;
                });
    }
    @Override
    public ProductResponse getProductDetail(String selectedProductName) {
        return Optional.ofNullable(productRepository.findByProductName(selectedProductName))
                .map(product -> ProductResponse.builder()
                        .productName(product.getProductName())
                        .merchant(product.getMerchant())
                        .price(product.getPrice())
                        .build())
                .orElse(null);
    }
    @Override
    public Page<Product> getProductsWithPagination(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
