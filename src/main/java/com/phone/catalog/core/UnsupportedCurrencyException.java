package com.phone.catalog.core;

public class UnsupportedCurrencyException extends RuntimeException {

    UnsupportedCurrencyException(final String message) {
        super(message);
    }

}