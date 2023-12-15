package com.binaracademy.binarfud.response;

import com.binaracademy.binarfud.model.Merchant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String productName;
    private Double price;
    private Merchant merchant;
}
