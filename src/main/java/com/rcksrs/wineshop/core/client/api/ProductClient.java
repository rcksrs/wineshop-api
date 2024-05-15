package com.rcksrs.wineshop.core.client.api;

import com.rcksrs.wineshop.core.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
@FeignClient(name = "product-client", url = "${client.product.url}")
public interface ProductClient {

    @GetMapping
    List<ProductResponse> getProducts();
}
