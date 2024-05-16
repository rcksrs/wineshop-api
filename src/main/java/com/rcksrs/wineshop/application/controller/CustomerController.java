package com.rcksrs.wineshop.application.controller;

import com.rcksrs.wineshop.core.dto.CustomerDTO;
import com.rcksrs.wineshop.core.dto.ProductDTO;
import com.rcksrs.wineshop.core.dto.TopCustomerDTO;
import com.rcksrs.wineshop.core.dto.TopPurchaseDTO;
import com.rcksrs.wineshop.core.usecase.ListPurchaseUseCase;
import com.rcksrs.wineshop.core.usecase.ProductRecommendationUseCase;
import com.rcksrs.wineshop.core.usecase.TopCustomersUseCase;
import com.rcksrs.wineshop.core.usecase.TopPurchaseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customer Controller")
public class CustomerController {
    private final ListPurchaseUseCase listPurchaseUseCase;
    private final TopPurchaseUseCase topPurchaseUseCase;
    private final TopCustomersUseCase topCustomersUseCase;
    private final ProductRecommendationUseCase productRecommendationUseCase;

    @GetMapping
    @Operation(summary = "Retorna a lista das compras ordenadas de forma crescente pelo valor total")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(listPurchaseUseCase.listAll());
    }

    @GetMapping("/top-purchase/{year}")
    @Operation(summary = "Retorna a maior compra do ano informado")
    public ResponseEntity<TopPurchaseDTO> topPurchases(@PathVariable Integer year) {
        return ResponseEntity.ok(topPurchaseUseCase.getTopPurchaseForYear(year));
    }

    @GetMapping("/top-customers")
    @Operation(summary = "Retorna os clientes que possuem mais compras recorrentes com maiores valores")
    public ResponseEntity<List<TopCustomerDTO>> topCustomers(@RequestParam(required = false, defaultValue = "3") Integer limit) {
        return ResponseEntity.ok(topCustomersUseCase.getTopCustomers(limit));
    }

    @GetMapping("{document}/recommendation")
    @Operation(summary = "Retorna uma recomendação de vinho baseado nos tipos de vinho que o cliente mais compra")
    public ResponseEntity<ProductDTO> topCustomers(@PathVariable String document) {
        return ResponseEntity.ok(productRecommendationUseCase.getRecommendation(document));
    }
}
