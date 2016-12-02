package org.paradise.microservice.userpreference.functional;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.paradise.microservice.userpreference.App;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for running Component tests using rest assured
 * https://github.com/jayway/rest-assured
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebIntegrationTest(randomPort = false)
@ActiveProfiles("local")
public abstract class AbstractFunctionalTest {

    @Value("${app.test.functional.baseurl}")
    protected String apiBaseUrl;

    @Before
    public void setUpBeforeTest() {

        RestAssured.reset();
    }

}
