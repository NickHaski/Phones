package com.phone.catalog;

import com.phone.catalog.core.PhoneEntity;
import com.phone.catalog.core.PhoneRepository;
import com.phone.catalog.core.PhoneService;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.phone.catalog.core.Currency.EUR;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class PhoneServiceTest {

    public static final String DESCRIPTION_2 = "description2";
    public static final String DESCRIPTION_1 = "description1";
    public static final String NAME_1 = "name1";
    public static final String NAME_2 = "name2";
    @Mock
    private PhoneRepository phoneRepository;

    private PhoneService phoneService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        phoneService = new PhoneService(phoneRepository);
    }

    @Test
    public void shouldReturnListWithPhones() {
        final ArrayList<PhoneEntity> phoneEntities = Lists.newArrayList(new PhoneEntity(), new PhoneEntity());
        final Page page = new PageImpl<>(phoneEntities, new PageRequest(0,10), 2);

        when(phoneRepository.findAll(any(Pageable.class))).thenReturn(page);

        final Pair<List<PhoneEntity>, Long> phones = phoneService.getPhones(new PageRequest(0,10));

        Assert.assertNotNull(phones);
        Assert.assertEquals(Long.valueOf(2) , phones.getRight());

    }

    @Test
    public void shouldReturnListWithPhonesPrices() {
        final PhoneEntity phoneEntity1 = new PhoneEntity(NAME_1, DESCRIPTION_1, 100, EUR);
        final PhoneEntity phoneEntity2 = new PhoneEntity(NAME_2, DESCRIPTION_2, 1000, EUR);

        final ArrayList<PhoneEntity> phoneEntities = Lists.newArrayList(phoneEntity1, phoneEntity2);
        when(phoneRepository.findByIdIn(any())).thenReturn(phoneEntities);

        final List<Long> list = Lists.newArrayList(1L,2L);

        final List<PhoneEntity> phones = phoneService.getPhonePrices(list);

        Assert.assertNotNull(phones);
        Assert.assertEquals(2 , phones.size());

        Assert.assertEquals(100, phones.get(0).getPriceCents());
        Assert.assertEquals(DESCRIPTION_1, phones.get(0).getDescription());
        Assert.assertEquals(NAME_1, phones.get(0).getName());
        Assert.assertEquals(EUR, phones.get(0).getCurrency());

        Assert.assertEquals(1000, phones.get(1).getPriceCents());
        Assert.assertEquals(DESCRIPTION_2, phones.get(1).getDescription());
        Assert.assertEquals(NAME_2, phones.get(1).getName());
        Assert.assertEquals(EUR, phones.get(1).getCurrency());
    }


}
