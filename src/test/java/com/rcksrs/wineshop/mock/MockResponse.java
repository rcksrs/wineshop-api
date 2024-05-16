package com.rcksrs.wineshop.mock;

import com.rcksrs.wineshop.core.client.dto.CustomerResponse;
import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import com.rcksrs.wineshop.core.client.dto.PurchaseResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MockResponse {

    public static List<ProductResponse> getProducts() {
        var product1 = new ProductResponse(1L, "type-1", 10.0, 2010, 2010);
        var product2 = new ProductResponse(2L, "type-1", 20.0, 2020, 2020);
        var product3 = new ProductResponse(3L, "type-2", 30.0, 2030, 2030);
        var product4 = new ProductResponse(4L, "type-1", 40.0, 2010, 2010);
        var product5 = new ProductResponse(5L, "type-2", 50.0, 2010, 2010);
        return Arrays.asList(product1, product2, product3, product4, product5);
    }

    public static List<CustomerResponse> getCustomers() {
        var customer1 = new CustomerResponse("customer-1", "document-1", Arrays.asList(
                new PurchaseResponse(2L, 2L),
                new PurchaseResponse(1L, 1L))
        );

        var customer2 = new CustomerResponse("customer-2", "document-2", Arrays.asList(
                new PurchaseResponse(2L, 2L),
                new PurchaseResponse(2L, 3L))
        );

        var customer3 = new CustomerResponse("customer-3", "document-3", List.of(
                new PurchaseResponse(3L, 10L))
        );

        var customer4 = new CustomerResponse("customer-4", "document-4", List.of(
                new PurchaseResponse(4L, 40L))
        );

        var customer5 = new CustomerResponse("customer-5", "document-5", null);

        var customer6 = new CustomerResponse("customer-6", "document-6", new ArrayList<>());

        var customer7 = new CustomerResponse("customer-7", "document-7", List.of(
                new PurchaseResponse(100L, 3L))
        );

        var customer8 = new CustomerResponse("customer-8", "document-8", List.of(
                new PurchaseResponse(3L, 1L),
                new PurchaseResponse(5L, 1L))
        );

        var customer9 = new CustomerResponse("customer-9", "document-9", List.of(
                new PurchaseResponse(1L, 1L),
                new PurchaseResponse(2L, 1L),
                new PurchaseResponse(3L, 1L),
                new PurchaseResponse(4L, 1L),
                new PurchaseResponse(5L, 1L))
        );

        return Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9);
    }
}
