package org.paradise.microservice.userpreference.functional;

import org.apache.http.HttpStatus;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

/**
 * Call the health endpoint.
 */
public class HealthFunctionalTest extends AbstractFunctionalTest {

    private static final String HEALTH_CHECK_URL = "/health";

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
