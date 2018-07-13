package com.phone.catalog.core;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PhoneController {

    private final PhoneService phoneService;
    private final PhoneConverter phoneConverter;
    private final PhonePriceConverter phonePriceConverter;

    @GetMapping(path = RestPath.Phone.ROOT)
    public ResponseEntity<CatalogResponse> getPhoneOrders(final Pageable pageable) {
        final Pair<List<PhoneEntity>, Long> phones = phoneService.getPhones(pageable);

        final Collection<PhoneResponse> phoneResponse = phoneConverter.convert(phones.getLeft());

        final CatalogResponse build = CatalogResponse.builder()
                .catalog(phoneResponse)
                .build();
        return ResponseEntity.ok(build);
    }

    @GetMapping(path = RestPath.Phone.PRICES)
    public ResponseEntity<CatalogPricesResponse> getPhonePrices(final @RequestParam List<Long> ids) {
        final List<PhoneEntity> phones = phoneService.getPhonePrices(ids);

        final Collection<PhonePricesResponse> phoneResponse = phonePriceConverter.convert(phones);

        return ResponseEntity.ok(CatalogPricesResponse.builder()
                .phonePrices(phoneResponse)
                .build());
    }

}
