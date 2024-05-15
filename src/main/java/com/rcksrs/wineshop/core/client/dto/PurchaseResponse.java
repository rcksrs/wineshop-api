package com.rcksrs.wineshop.core.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PurchaseResponse(
        @JsonProperty(value = "codigo") Long code,
        @JsonProperty(value = "quantidade") Long quantity
) { }
