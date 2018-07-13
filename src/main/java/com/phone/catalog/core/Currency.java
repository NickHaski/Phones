package com.phone.catalog.core;

import java.util.Arrays;

public enum Currency {

    EUR;

    Currency getByName(final String value) {
        return Arrays.stream(values()).filter(v -> value.equalsIgnoreCase(v.name()))
                .findFirst()
                .orElseThrow(() -> new UnsupportedCurrencyException("Unsupported currency."));
    }

}
