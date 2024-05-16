package com.rcksrs.wineshop.core.dto;

import com.rcksrs.wineshop.core.client.dto.CustomerResponse;

import java.util.List;

public record TopCustomerDTO(String name, String document, Integer purchases, Double total) {
    public TopCustomerDTO(CustomerResponse customer, List<PurchaseDTO> purchases) {
        this(customer.name(), customer.document(), purchases.size(), calculateTotalPurchases(purchases));
    }

    private static Double calculateTotalPurchases(List<PurchaseDTO> purchases) {
        return purchases.stream().mapToDouble(PurchaseDTO::total).reduce(0, Double::sum);
    }
}
