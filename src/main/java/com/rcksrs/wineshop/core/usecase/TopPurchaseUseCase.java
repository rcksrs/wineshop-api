package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import com.rcksrs.wineshop.core.client.dto.CustomerResponse;
import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import com.rcksrs.wineshop.core.dto.PurchaseDTO;
import com.rcksrs.wineshop.core.dto.TopPurchaseDTO;
import com.rcksrs.wineshop.core.exception.PurchaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class TopPurchaseUseCase {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public TopPurchaseDTO getTopPurchaseForYear(Integer year) {
        var productsMap = productClient.getProducts()
                .stream()
                .filter(p -> p.purchaseYear().equals(year))
                .collect(toMap(ProductResponse::code, p -> p));

        return customerClient.getCustomers()
                .stream()
                .filter(customer -> customer.purchases() != null)
                .map(customer -> processPurchases(customer, productsMap))
                .filter(Objects::nonNull)
                .max(comparingDouble(c -> c.purchase().total()))
                .orElseThrow(PurchaseNotFoundException::new);
    }

    private TopPurchaseDTO processPurchases(CustomerResponse customer, Map<Long, ProductResponse> productsMap) {
        return customer.purchases()
                .stream()
                .filter(p -> productsMap.containsKey(p.code()))
                .map(p -> new PurchaseDTO(p, productsMap.get(p.code())))
                .max(comparingDouble(PurchaseDTO::total))
                .map(p -> new TopPurchaseDTO(customer.name(), customer.document(), p))
                .orElse(null);
    }
}
