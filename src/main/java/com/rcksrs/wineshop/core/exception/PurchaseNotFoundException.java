package com.rcksrs.wineshop.core.exception;

import com.rcksrs.wineshop.core.exception.shared.NotFoundException;

public class PurchaseNotFoundException extends NotFoundException {
    public PurchaseNotFoundException() {
        super("Purchase was not found");
    }
}
