package org.paradise.microservice.userpreference.functional;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.paradise.microservice.userpreference.Constants;
import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * Created by terrence on 2/12/2016.
 */
public class UserPreferenceFunctionalTest extends AbstractFunctionalTest {

    public static final Logger LOG = LoggerFactory.getLogger(UserPreferenceFunctionalTest.class);

    public static final String APCN = "88886666";
    public static final String APCN_NEXT = "99997777";

    @Value("${local.server.port}")
    private int port;

    private HttpRequest httpRequest;

    @Before
    public void setUp() {

        RestAssured.reset();

        RestAssured.port = port;

        // mock User Preference index item
        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.UpdateItem")
                .withBody(toRegexBody("\\{\\\"TableName\\\":\\\"USER_PREFERENCE_INDEX\\\".*"));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBUpdateIndexItemResponse.json"))));
    }

    @Test
    public void shouldCreateAndUpdateUserPreferences() throws Exception {

        // Create User Preferences
        String requestBodyForCreate = "{\n"
                + "    \"data\": {\n"
                + "        \"preferences\" : {\n"
                + "            \"token_metadata\" : {\n"
                + "                 \"date_time_created\":\"2015-03-11T12:27:06.000Z\",\n"
                + "                 \"date_time_expired\":\"2018-03-11T12:27:06.000Z\",\n"
                + "                 \"date_time_last_sync\":\"2016-03-11T12:27:06.000Z\"\n"
                + "            },\n"
                + "            \"token\": \"u20K1UI.d5wAAAFYnhkTVpjW\"\n"
                + "        }\n"
                + "    }\n"
                + "}";

        // mock User Preference item
        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.UpdateItem")
                .withBody(toRegexBody("\\{\\\"TableName\\\":\\\"USER_PREFERENCE\\\".*2015-03-11T12:27:06.*"));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBUpdateItemResponse1.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.GetItem")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBGetItemRequest1.json")));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBGetItemResponse1.json"))));

        Response response =
            given()
                    .header("Content-Type", "application/json")
                    .header(Constants.HTTP_HEADERS_APCN, APCN)
                    .body(requestBodyForCreate)
            .when()
                    .post(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY)
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
                    .body("preferences.token", equalTo("u20K1UI.d5wAAAFYnhkTVpjW"))
                    .extract().response();

        LOG.info("shouldCreateAndUpdateUserPreferences step 1 HTTP Response: [{}]", response.asString());

        // Update User Preferences
        String requestBodyForUpdate = "{\n"
                + "    \"data\": {\n"
                + "        \"preferences\" : {\n"
                + "            \"token_metadata\" : {\n"
                + "                 \"date_time_created\":\"1985-12-01T12:27:06.000Z\",\n"
                + "                 \"date_time_expired\":\"2020-12-31T12:27:06.000Z\",\n"
                + "                 \"date_time_last_sync\":\"2000-01-11T12:27:06.000Z\"\n"
                + "            },\n"
                + "            \"token\": \"AustraliaPostSecurityToken\"\n"
                + "        }\n"
                + "    }\n"
                + "}";

        //  Clear all expectations that match the http
        mockServerClient.clear(httpRequest);

        mockServerClient.when(httpRequest, Times.exactly(1))
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBGetItemResponse2.json"))));

        mockServerClient.when(httpRequest, Times.exactly(2))
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBGetItemResponse2.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.UpdateItem")
                .withBody(toRegexBody("\\{\\\"TableName\\\":\\\"USER_PREFERENCE\\\".*1985-12-01T12:27:06.*"));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBUpdateItemResponse2.json"))));

        response =
            given()
                    .header("Content-Type", "application/json")
                    .header(Constants.HTTP_HEADERS_APCN, APCN)
                    .body(requestBodyForUpdate)
            .when()
                    .post(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY)
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
                    .body("preferences.token", equalTo("AustraliaPostSecurityToken"))
                    .extract().response();

        LOG.info("shouldCreateAndUpdateUserPreferences step 2 HTTP Response: [{}]", response.asString());
    }

    @Test
    public void shouldCreateAndGetUserPreferences() throws Exception {

        // Create User Preferences
        String requestBodyForCreate = "{\n"
                + "    \"data\": {"
                + "        \"preferences\" : {\n"
                + "            \"token_metadata\" : {\n"
                + "                 \"date_time_created\":\"9915-03-11T12:27:06.000Z\",\n"
                + "                 \"date_time_expired\":\"9918-03-11T12:27:06.000Z\",\n"
                + "                 \"date_time_last_sync\":\"9916-03-11T12:27:06.000Z\"\n"
                + "            },\n"
                + "            \"token\": \"ReallyGoodSecurityToken\"\n"
                + "        }\n"
                + "    }\n"
                + "}";

        // mock User Preference item
        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.UpdateItem")
                .withBody(toRegexBody("\\{\\\"TableName\\\":\\\"USER_PREFERENCE\\\".*9915-03-11T12:27:06.*"));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBUpdateItemResponse3.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.GetItem")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBGetItemRequest3.json")));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBGetItemResponse3.json"))));

        given()
                .header("Content-Type", "application/json")
                .header(Constants.HTTP_HEADERS_APCN, APCN_NEXT)
                .body(requestBodyForCreate)
        .when()
                .post(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("c_number", equalTo(APCN_NEXT))
                .body("date_time_created", notNullValue())
                .body("preference_type", equalTo(PreferenceType.EBAY.toString()))
                .body("preferences.token_metadata.date_time_created", equalTo("9915-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_expired", equalTo("9918-03-11T12:27:06.000Z"))
                .body("preferences.token_metadata.date_time_last_sync", equalTo("9916-03-11T12:27:06.000Z"))
                .body("preferences.token", equalTo("ReallyGoodSecurityToken"));

        // Get User Preferences
        given()
                .header("Content-Type", "application/json")
                .header(Constants.HTTP_HEADERS_APCN, APCN_NEXT)
                .body(requestBodyForCreate)
        .when()
                .get(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("c_number", equalTo(APCN_NEXT))
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
                .header("Content-Type", "application/json")
                .header(Constants.HTTP_HEADERS_APCN, APCN)
                .body("{\"data\": {\"preferences\" : {}}}")
        .when()
                .post(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + "Invalid_Preference_Type")
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("exception", equalTo("org.paradise.microservice.userpreference.exception.UserPreferenceException"))
                .body("message",
                        equalTo("No enum constant org.paradise.microservice.userpreference.domain.PreferenceType.Invalid_Preference_Type"))
                .body("path", equalTo(Constants.REQUEST_PATH_USER_PREFERENCE + "/Invalid_Preference_Type"));
    }

    @Test
    public void shouldThrowErrorWithoutAPCNInHttpHeaders() throws Exception {

        given()
                .header("Content-Type", "application/json")
                .body("{\"data\": {\"preferences\" : {}}}")
        .when()
                .post(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY)
        .then()
                .log().all()
        .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("exception", equalTo("org.springframework.web.bind.ServletRequestBindingException"))
                .body("message", equalTo("Missing request header '" + Constants.HTTP_HEADERS_APCN + "' for method parameter of type String"))
                .body("path", equalTo(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY));
    }

    @Test
    public void shouldThrowErrorWithoutPreferencesInBody() throws Exception {

        given()
                .header("Content-Type", "application/json")
                .header(Constants.HTTP_HEADERS_APCN, APCN)
                .body("{\"data\": {}}")
        .when()
                .post(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY)
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
                .body("path", equalTo(Constants.REQUEST_PATH_USER_PREFERENCE + "/" + PreferenceType.EBAY));
    }

}