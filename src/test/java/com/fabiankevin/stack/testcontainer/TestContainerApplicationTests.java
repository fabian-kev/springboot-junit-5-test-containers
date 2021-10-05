package com.fabiankevin.stack.testcontainer;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class TestContainerApplicationTests {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetUsers getUsers;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
    }

//    @Load
    @BeforeEach
    public void load(){
        userRepository.save(
                UserEntity.builder()
                        .name("Kevin Fabian")
                        .build()
        );
    }

    @Test
    void contextLoads() {
        assertEquals( 1, userRepository.findAll().size());
        assertTrue(userRepository.findById(1l).isPresent());

        UserEntity userEntity = userRepository.findById(1l).get();
        assertEquals(userEntity.getName(), "Kevin Fabian");

    }

    @Test
    public void execute_shouldReturnOneUser(){
       assertEquals( getUsers.execute().size(), 1);
    }

}
