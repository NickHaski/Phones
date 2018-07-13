package com.phone.catalog.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneResponse {

    private final String name;
    private final String description;
    private final String photoUrl;
    private final String price;

}
