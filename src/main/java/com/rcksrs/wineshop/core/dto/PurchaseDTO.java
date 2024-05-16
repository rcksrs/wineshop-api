package com.rcksrs.wineshop.core.dto;

import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import com.rcksrs.wineshop.core.client.dto.PurchaseResponse;

public record PurchaseDTO(Long code, Long quantity, Double total, String type, Double price, Integer harvest, Integer purchaseYear) {

    public PurchaseDTO(PurchaseResponse purchase, ProductResponse product) {
        this(purchase.code(),
                purchase.quantity(),
                purchase.quantity() * product.price(),
                product.type(), product.price(),
                product.harvest(),
                product.purchaseYear()
        );
    }
}
