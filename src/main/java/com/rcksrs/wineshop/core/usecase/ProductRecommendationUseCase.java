package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import com.rcksrs.wineshop.core.client.dto.PurchaseResponse;
import com.rcksrs.wineshop.core.dto.ProductDTO;
import com.rcksrs.wineshop.core.exception.CustomerNotFoundException;
import com.rcksrs.wineshop.core.exception.ProductNotFoundException;
import com.rcksrs.wineshop.core.exception.PurchaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ProductRecommendationUseCase {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public ProductDTO getRecommendation(String document) {
        var products = productClient.getProducts();
        var productsMap = products.stream().collect(toMap(ProductResponse::code, p -> p));

        var customer = customerClient.getCustomers()
                .stream()
                .filter(c -> c.document().equals(document))
                .findFirst()
                .orElseThrow(CustomerNotFoundException::new);

        if (customer.purchases() == null) throw new PurchaseNotFoundException();

        var recommendationTypes = customer.purchases()
                .stream()
                .filter(p -> productsMap.containsKey(p.code()))
                .map(p -> productsMap.get(p.code()))
                .collect(groupingBy(ProductResponse::type, counting()))
                .entrySet().stream()
                .sorted(comparingByValue(reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();

        var notRecommendedCodes = customer.purchases()
                .stream()
                .map(PurchaseResponse::code)
                .toList();

        for (String type : recommendationTypes) {
            var recommendation = products.stream()
                    .filter(p -> p.type().equals(type) && !notRecommendedCodes.contains(p.code()))
                    .findFirst()
                    .map(ProductDTO::new);
            if (recommendation.isPresent()) return recommendation.get();
        }

        return products.stream()
                .filter(p -> !notRecommendedCodes.contains(p.code()))
                .findAny()
                .map(ProductDTO::new)
                .orElseGet(() -> products.stream().findAny().map(ProductDTO::new).orElseThrow(ProductNotFoundException::new));
    }
}
