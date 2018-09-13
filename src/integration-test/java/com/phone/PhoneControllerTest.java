package com.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({Profiles.LOCAL})
public class PhoneControllerTest {

    private static final int MYSQL_PORT = 3306;

    private static final Long ORDER_ID_1 = 1L;
    private static final String FIRST_NAME = "FirstName";
    private static final String SECOND_NAME = "SecondName";
    private static final String EMAIL = "test@gmail.email";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    @InjectMocks
    private PhoneService phoneService;

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
        orderRepository.deleteAll();
    }

    @Test
    public void shouldCreateOrder() throws Exception {

    }

}
