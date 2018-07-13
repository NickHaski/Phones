package com.phone.catalog.core;

import org.springframework.stereotype.Component;

@Component
public class PhonePriceConverter implements Converter<PhoneEntity, PhonePricesResponse> {
    @Override
    public PhonePricesResponse convert(final PhoneEntity phoneEntity) {
        return PhonePricesResponse.builder()
                .priceCents(phoneEntity.getPriceCents())
                .currency(phoneEntity.getCurrency())
                .id(phoneEntity.getId())
                .build();
    }
}
