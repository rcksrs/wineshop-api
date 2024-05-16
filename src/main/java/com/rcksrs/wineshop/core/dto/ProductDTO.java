package com.rcksrs.wineshop.core.dto;

import com.rcksrs.wineshop.core.client.dto.ProductResponse;

public record ProductDTO(Long code, String type, Double price, Integer harvest) {
    public ProductDTO(ProductResponse product) {
        this(product.code(), product.type(), product.price(), product.harvest());
    }
}
