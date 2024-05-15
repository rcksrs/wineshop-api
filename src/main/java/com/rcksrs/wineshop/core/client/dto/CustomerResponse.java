package com.rcksrs.wineshop.core.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CustomerResponse(
        @JsonProperty(value = "nome") String name,
        @JsonProperty(value = "cpf") String document,
        @JsonProperty(value = "compras") List<PurchaseResponse> purchases
) { }
