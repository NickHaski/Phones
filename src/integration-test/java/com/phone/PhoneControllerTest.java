package com.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phone.catalog.Application;
import com.phone.catalog.core.CatalogPricesResponse;
import com.phone.catalog.core.CatalogResponse;
import com.phone.catalog.core.Currency;
import com.phone.catalog.core.PhoneEntity;
import com.phone.catalog.core.PhonePricesResponse;
import com.phone.catalog.core.PhoneRepository;
import com.phone.catalog.core.PhoneResponse;
import com.phone.catalog.core.RestPath;
import com.phone.catalog.profile.Profiles;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({Profiles.LOCAL})
public class PhoneControllerTest {

    private static final int MYSQL_PORT = 3306;
    public static final String TEST_PHONE_1 = "TestPhone1";
    public static final String DESCRIPTION_1 = "description1";
    public static final String TEST_PHONE_2 = "TestPhone2";
    public static final String DESCRIPTION_2 = "description2";
    public static final int PHONE_PRICE_1 = 100;
    public static final int PHONE_PRICE_2 = 200;


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PhoneRepository phoneRepository;

    @ClassRule
    public static GenericContainer mysql = new GenericContainer("mysql:latest")
            .withEnv("MYSQL_ROOT_PASSWORD", "root")
            .withClasspathResourceMapping("mysql", "/docker-entrypoint-initdb.d", BindMode.READ_ONLY)
            .withExposedPorts(MYSQL_PORT);

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeClass
    public static void initialize() throws InterruptedException {
        // Configure mysql
        System.setProperty("mysql.port", String.valueOf(mysql.getMappedPort(MYSQL_PORT)));
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper = new ObjectMapper();
    }

    @After
    public void cleanUp() {
        phoneRepository.deleteAll();
    }

    @Test
    public void shouldReturnPhoneList() throws Exception {

        final PhoneEntity phoneEntity1 = new PhoneEntity(TEST_PHONE_1, DESCRIPTION_1, PHONE_PRICE_1, Currency.EUR);
        final PhoneEntity phoneEntity2 = new PhoneEntity(TEST_PHONE_2, DESCRIPTION_2, PHONE_PRICE_2, Currency.EUR);

        phoneRepository.save(phoneEntity1);
        phoneRepository.save(phoneEntity2);

        final MvcResult mvcResult = mockMvc.perform(get(RestPath.Phone.ROOT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final CatalogResponse catalogResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CatalogResponse.class);

        assertNotNull(catalogResponse.getCatalog());
        assertEquals(2, Lists.newArrayList(catalogResponse.getCatalog()).size());
        assertEquals(TEST_PHONE_1, Lists.newArrayList(catalogResponse.getCatalog()).get(0).getName());
        assertEquals(TEST_PHONE_2, Lists.newArrayList(catalogResponse.getCatalog()).get(1).getName());

        assertEquals(DESCRIPTION_1, Lists.newArrayList(catalogResponse.getCatalog()).get(0).getDescription());
        assertEquals(DESCRIPTION_2, Lists.newArrayList(catalogResponse.getCatalog()).get(1).getDescription());

        assertEquals("1", Lists.newArrayList(catalogResponse.getCatalog()).get(0).getPrice());
        assertEquals("2", Lists.newArrayList(catalogResponse.getCatalog()).get(1).getPrice());

    }

    @Test
    public void shouldReturnPhonePrices() throws Exception {

        final PhoneEntity phoneEntity1 = new PhoneEntity(TEST_PHONE_1, DESCRIPTION_1, PHONE_PRICE_1, Currency.EUR);
        final PhoneEntity phoneEntity2 = new PhoneEntity(TEST_PHONE_2, DESCRIPTION_2, PHONE_PRICE_2, Currency.EUR);

        phoneRepository.save(phoneEntity1);
        phoneRepository.save(phoneEntity2);

        final MvcResult mvcResult = mockMvc.perform(get(RestPath.Phone.PRICES)
                .param("ids", "1,2,3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final CatalogPricesResponse pricesResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CatalogPricesResponse.class);

        assertNotNull(pricesResponse.getPhonePrices());

        List<PhonePricesResponse> phonePrices = Lists.newArrayList(pricesResponse.getPhonePrices());

        assertEquals(2, phonePrices.size());

        assertEquals(PHONE_PRICE_1, phonePrices.get(0).getPriceCents());
        assertEquals(PHONE_PRICE_2, phonePrices.get(1).getPriceCents());

    }
}
