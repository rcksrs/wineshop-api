package com.rcksrs.wineshop.core.exception;

import com.rcksrs.wineshop.core.exception.shared.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super("Product was not found");
    }
}
