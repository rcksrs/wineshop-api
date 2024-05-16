package com.rcksrs.wineshop.core.dto;

import java.util.List;

public record CustomerDTO(String name, String document, List<PurchaseDTO> purchases) {

}
