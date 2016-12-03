package org.paradise.microservice.userpreference.functional;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.paradise.microservice.userpreference.domain.PreferenceType;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by terrence on 2/12/2016.
 */
public class UserPreferenceFunctionalTest extends AbstractFunctionalTest {

    public static final String USER_PREFERENCE_URL = "/user/preferences";
    public static final String AP_CUSTOMER_NUMBER = "ap-customer-number";
    public static final String APCN = "88886666";
    public static final String APCN_SECOND = "99997777";

    @Test
    public void shouldCreateAndUpdateUserPreferences() throws Exception {

        // Create User Preferences
        String requestBodyForCreate = "{\n"
                + "    \"preferences\" : {\n"
                + "        \"token_metadata\" : {\n"
                + "             \"date_time_created\":\"2015-03-11T12:27:06.000Z\",\n"
                + "             \"date_time_expired\":\"2018-03-11T12:27:06.000Z\",\n"
                + "             \"date_time_last_sync\":\"2016-03-11T12:27:06.000Z\"\n"
                + "        },\n"
                + "        \"token\": \"u20K1UI.d5wAAAFYnhkTVpjW\"\n"
                + "    }\n"
                + "}";

        given()
        .when()
                .header("Content-Type", "application/json")
                .header(AP_CUSTOMER_NUMBER, APCN)
                .body(requestBodyForCreate)
                .post(apiBaseUrl + USER_PREFERENCE_URL + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
                .assertThat()
        .statusCode(HttpStatus.SC_CREATED)
                .body("c_number", equalTo(APCN))
                .body("date_time_created", notNullValue())
                .body("preference_type", equalTo(PreferenceType.EBAY.toString()))
                .body("preferences.token_metadata.date_time_created", equalTo("2015-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_expired", equalTo("2018-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_last_sync", equalTo("2016-03-11T12:27:06.000Z"))
                .body("preferences.token", equalTo("u20K1UI.d5wAAAFYnhkTVpjW"));

        // Update User Preferences
        String requestBodyForUpdate = "{\n"
                + "    \"preferences\" : {\n"
                + "        \"token_metadata\" : {\n"
                + "             \"date_time_created\":\"1985-12-01T12:27:06.000Z\",\n"
                + "             \"date_time_expired\":\"2020-12-31T12:27:06.000Z\",\n"
                + "             \"date_time_last_sync\":\"2000-01-11T12:27:06.000Z\"\n"
                + "        },\n"
                + "        \"token\": \"AustraliaPostSecurityToken\"\n"
                + "    }\n"
                + "}";

        given()
        .when()
                .header("Content-Type", "application/json")
                .header(AP_CUSTOMER_NUMBER, APCN)
                .body(requestBodyForUpdate)
                .post(apiBaseUrl + USER_PREFERENCE_URL + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("c_number", equalTo(APCN))
                .body("date_time_created", notNullValue())
                .body("date_time_updated", notNullValue())
                .body("preference_type", equalTo(PreferenceType.EBAY.toString()))
                .body("preferences.token_metadata.date_time_created", equalTo("1985-12-01T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_expired", equalTo("2020-12-31T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_last_sync", equalTo("2000-01-11T12:27:06.000Z"))
                .body("preferences.token", equalTo("AustraliaPostSecurityToken"));
    }

    @Test
    public void shouldCreateAndGetUserPreferences() throws Exception {

        // Create User Preferences
        String requestBodyForCreate = "{\n"
                + "    \"preferences\" : {\n"
                + "        \"token_metadata\" : {\n"
                + "             \"date_time_created\":\"9915-03-11T12:27:06.000Z\",\n"
                + "             \"date_time_expired\":\"9918-03-11T12:27:06.000Z\",\n"
                + "             \"date_time_last_sync\":\"9916-03-11T12:27:06.000Z\"\n"
                + "        },\n"
                + "        \"token\": \"ReallyGoodSecurityToken\"\n"
                + "    }\n"
                + "}";

        given()
        .when()
                .header("Content-Type", "application/json")
                .header(AP_CUSTOMER_NUMBER, APCN_SECOND)
                .body(requestBodyForCreate)
                .post(apiBaseUrl + USER_PREFERENCE_URL + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("c_number", equalTo(APCN_SECOND))
                .body("date_time_created", notNullValue())
                .body("preference_type", equalTo(PreferenceType.EBAY.toString()))
                .body("preferences.token_metadata.date_time_created", equalTo("9915-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_expired", equalTo("9918-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_last_sync", equalTo("9916-03-11T12:27:06.000Z"))
                .body("preferences.token", equalTo("ReallyGoodSecurityToken"));

        // Get User Preferences
        given()
        .when()
                .header("Content-Type", "application/json")
                .header(AP_CUSTOMER_NUMBER, APCN_SECOND)
                .body(requestBodyForCreate)
                .get(apiBaseUrl + USER_PREFERENCE_URL + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("c_number", equalTo(APCN_SECOND))
                .body("date_time_created", notNullValue())
                .body("preference_type", equalTo(PreferenceType.EBAY.toString()))
                .body("preferences.token_metadata.date_time_created", equalTo("9915-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_expired", equalTo("9918-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_last_sync", equalTo("9916-03-11T12:27:06.000Z"))
                .body("preferences.token", equalTo("ReallyGoodSecurityToken"));
    }

    @Test
    public void shouldThrowErrorWithInvalidPreferenceType() throws Exception {

        given()
        .when()
                .header("Content-Type", "application/json")
                .header(AP_CUSTOMER_NUMBER, APCN)
                .body("{\"preferences\" : {}}")
                .post(apiBaseUrl + USER_PREFERENCE_URL + "/" + "Invalid_Preference_Type")
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("exception", equalTo("org.paradise.microservice.userpreference.exception.UserPreferenceException"))
                .body("message",
                        equalTo("No enum constant org.paradise.microservice.userpreference.domain.PreferenceType.Invalid_Preference_Type"))
                .body("path", equalTo("/user/preferences/Invalid_Preference_Type"));
    }

    @Test
    public void shouldThrowErrorWithoutAPCNInHttpHeaders() throws Exception {

        given()
        .when()
                .header("Content-Type", "application/json")
                .body("{\"preferences\" : {}}")
                .post(apiBaseUrl + USER_PREFERENCE_URL + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("exception", equalTo("org.springframework.web.bind.ServletRequestBindingException"))
                .body("message", equalTo("Missing request header 'ap-customer-number' for method parameter of type String"))
                .body("path", equalTo("/user/preferences/EBAY"));
    }

    @Test
    public void shouldThrowErrorWithoutPreferencesInBody() throws Exception {

        given()
        .when()
                .header("Content-Type", "application/json")
                .header(AP_CUSTOMER_NUMBER, APCN)
                .body("{}")
                .post(apiBaseUrl + USER_PREFERENCE_URL + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("exception", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"))
                .body("errors[0].defaultMessage", equalTo("may not be null"))
                .body("errors[0].objectName", equalTo("userPreferences"))
                .body("errors[0].field", equalTo("preferences"))
                .body("errors[0].rejectedValue", equalTo(null))
                .body("errors[0].bindingFailure", equalTo(Boolean.FALSE))
                .body("errors[0].code", equalTo("NotNull"))
                .body("message", equalTo("Validation failed for object='userPreferences'. Error count: 1"))
                .body("path", equalTo("/user/preferences/EBAY"));
    }

}