package com.fpm_2025.fpm_microservice_libs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test for fpm-common library
 * Excludes DataSource auto-configuration since this is a library, not an application
 */
@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
class FpmMicroserviceLibsApplicationTests {

    @Test
    void contextLoads() {
        // Library context loads successfully without DataSource
        System.out.println("fpm-common library loaded successfully!");
    }
}