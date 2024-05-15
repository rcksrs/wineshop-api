package com.rcksrs.wineshop.core.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductResponse(
        @JsonProperty(value = "codigo") Long code,
        @JsonProperty(value = "tipo_vinho") String type,
        @JsonProperty(value = "preco") Double price,
        @JsonProperty(value = "safra") Integer harvest,
        @JsonProperty(value = "ano_compra") Integer purchaseYear
) { }
