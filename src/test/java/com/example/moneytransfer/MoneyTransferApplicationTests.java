package com.example.moneytransfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    private static final GenericContainer<?> myApp = new GenericContainer<>("myapp:latest")
            .withExposedPorts(5500);

    @Test
    void contextLoads() {
        Integer appPort = myApp.getMappedPort(5500);
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + appPort, String.class);
    }
}
