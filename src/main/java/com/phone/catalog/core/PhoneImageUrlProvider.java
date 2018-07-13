package com.phone.catalog.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class PhoneImageUrlProvider {

    @Value("${phone.image.uri}")
    private String phoneImageHost;

    @Value("${phone.image.extension}")
    private String imageExtencion;

    public URI getUrlForPhone(final Long phoneId) {
        return UriComponentsBuilder.fromHttpUrl(phoneImageHost)
                .pathSegment(phoneId.toString() + imageExtencion)
                .build().toUri();
    }

}
