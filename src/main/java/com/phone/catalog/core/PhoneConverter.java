package com.phone.catalog.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class PhoneConverter implements Converter<PhoneEntity, PhoneResponse>{

    private final PhoneImageUrlProvider phoneImageUrlProvider;

    @Override
    public PhoneResponse convert(final PhoneEntity phoneEntity) {
        return PhoneResponse.builder()
                .name(phoneEntity.getName())
                .description(phoneEntity.getDescription())
                .price(MoneyConverter.toEuro(phoneEntity.getPriceCents()))
                .photoUrl(phoneImageUrlProvider.getUrlForPhone(phoneEntity.getId()).toString())
                .build();
    }

}
