package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import com.rcksrs.wineshop.core.client.dto.CustomerResponse;
import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import com.rcksrs.wineshop.core.dto.CustomerDTO;
import com.rcksrs.wineshop.core.dto.PurchaseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListPurchaseUseCase {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public List<CustomerDTO> listAll() {
        var productsMap = productClient.getProducts()
                .stream()
                .collect(Collectors.toMap(ProductResponse::code, p -> p));

        return customerClient.getCustomers()
                .stream()
                .map(c -> processPurchases(c, productsMap))
                .toList();
    }

    private CustomerDTO processPurchases(CustomerResponse customer, Map<Long, ProductResponse> productsMap) {
        var purchases = customer.purchases() != null ?
                customer.purchases().stream()
                        .filter(p -> productsMap.containsKey(p.code()))
                        .map(p -> new PurchaseDTO(p, productsMap.get(p.code())))
                        .sorted(Comparator.comparingDouble(PurchaseDTO::total))
                        .toList()
                : new ArrayList<PurchaseDTO>();
        return new CustomerDTO(customer.name(), customer.document(), purchases);
    }
}
