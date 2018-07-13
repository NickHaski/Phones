package com.phone.catalog.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PhonePricesResponse {

    private final Long id;
    private final int priceCents;
    private final Currency currency;

}
