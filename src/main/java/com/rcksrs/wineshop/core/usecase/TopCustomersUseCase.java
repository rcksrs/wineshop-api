package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import com.rcksrs.wineshop.core.client.dto.CustomerResponse;
import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import com.rcksrs.wineshop.core.dto.PurchaseDTO;
import com.rcksrs.wineshop.core.dto.TopCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class TopCustomersUseCase {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public List<TopCustomerDTO> getTopCustomers(Integer limit) {
        var productsMap = productClient.getProducts()
                .stream()
                .collect(toMap(ProductResponse::code, p -> p));

        var sorting = comparingDouble(TopCustomerDTO::purchases).reversed()
                .thenComparing(comparingDouble(TopCustomerDTO::total).reversed());

        return customerClient.getCustomers()
                .stream()
                .filter(customer -> customer.purchases() != null)
                .map(customer -> processPurchases(customer, productsMap))
                .sorted(sorting)
                .limit(abs(limit))
                .toList();
    }

    private TopCustomerDTO processPurchases(CustomerResponse customer, Map<Long, ProductResponse> productsMap) {
        var purchases = customer.purchases()
                .stream()
                .filter(p -> productsMap.containsKey(p.code()))
                .map(p -> new PurchaseDTO(p, productsMap.get(p.code())))
                .toList();
        return new TopCustomerDTO(customer, purchases);
    }
}
