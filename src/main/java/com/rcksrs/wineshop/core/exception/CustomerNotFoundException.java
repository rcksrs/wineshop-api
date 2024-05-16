package com.rcksrs.wineshop.core.exception;

import com.rcksrs.wineshop.core.exception.shared.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException() {
        super("Customer was not found for the provided document");
    }
}
