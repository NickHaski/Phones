package com.phone.catalog.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestPath {

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Phone {
        public static final String ROOT = "/phones";
        public static final String PRICES = ROOT + "/prices";
    }

}
