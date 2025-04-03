package org.springdemo.springproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class SpringProjectApplicationTests {

    @Test
    void contextLoads() {
        // This checks if the Spring context loads successfully
    }

}
