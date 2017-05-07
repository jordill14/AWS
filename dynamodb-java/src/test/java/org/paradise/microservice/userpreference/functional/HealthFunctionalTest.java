package org.paradise.microservice.userpreference.functional;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

/**
 * Call the health endpoint.
 */
public class HealthFunctionalTest extends AbstractFunctionalTest {

    private static final String HEALTH_CHECK_URL = "/health";

    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() {

        RestAssured.reset();

        RestAssured.port = port;
    }

    @Test
    public void shouldCheckHealth() {

        given()
        .when()
                .get(HEALTH_CHECK_URL)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_SERVICE_UNAVAILABLE)
                .body("status", is("DOWN"));
    }

}
