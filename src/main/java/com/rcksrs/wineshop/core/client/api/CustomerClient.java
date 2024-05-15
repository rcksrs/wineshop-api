package com.rcksrs.wineshop.core.client.api;

import com.rcksrs.wineshop.core.client.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
@FeignClient(name = "customer-client", url = "${client.customer.url}")
public interface CustomerClient {

    @GetMapping
    List<CustomerResponse> getCustomers();
}
