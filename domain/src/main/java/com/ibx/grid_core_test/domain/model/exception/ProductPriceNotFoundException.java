package com.ibx.grid_core_test.domain.model.exception;

public class ProductPriceNotFoundException extends RuntimeException {

    public ProductPriceNotFoundException(final String message) {
        super(message);
    }
}
