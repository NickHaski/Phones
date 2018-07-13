package com.phone.catalog.core;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public Pair<List<PhoneEntity>, Long> getPhones(final Pageable pageable) {
        final Page<PhoneEntity> all = phoneRepository.findAll(pageable);

        return all.getTotalElements() > 0 ? Pair.of(all.getContent(), all.getTotalElements()) :
                Pair.of(new ArrayList<>(0), 0L);
    }

    public List<PhoneEntity>  getPhonePrices(final List<Long> ids) {
        return phoneRepository.findByIdIn(ids);
    }
}
