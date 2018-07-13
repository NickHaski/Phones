package com.phone.catalog.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder
public class CatalogPricesResponse {

    private final Collection<PhonePricesResponse> phonePrices;

}
