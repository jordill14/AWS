package org.paradise.microservice.userpreference.functional;

import com.jayway.restassured.RestAssured;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Body;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.OutboundHttpRequest;
import org.mockserver.model.RegexBody;
import org.mockserver.model.StringBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * Base class for running Component tests using rest assured https://github.com/jayway/rest-assured
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("local")
public abstract class AbstractFunctionalTest {

    protected static MockServerClient mockServerClient;

    @Value("${app.test.functional.baseurl}")
    protected String apiBaseUrl;


    static {
        new ClientAndServer().startClientAndServer(8000);

        mockServerClient = new OurMockServerClient("localhost", 8000, "");

        initMockDynamoDB();
    }

    @Before
    public void setUp() {

        RestAssured.reset();

        mockServerClient.retrieveExistingExpectations(request().withMethod("GET"));

        // This is a work around to stop the mocksever trust store getting in the way
        // This value will placed in the properties on the first request, however it is not used.
        System.getProperties().remove("javax.net.ssl.trustStore");
    }

    public static Body toJsonBody(Resource resource) {

        try {
            return new JsonBody(IOUtils.toString(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Body toStringBody(Resource resource) {

        try {
            return new StringBody(IOUtils.toString(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Body toRegexBody(String regexBody) {

       return new RegexBody(regexBody);
    }

    public static class OurMockServerClient extends MockServerClient {

        public OurMockServerClient(String host, int port, String contextPath) {
            super(host, port, contextPath);
        }

        protected HttpResponse sendRequest(HttpRequest httpRequest) {

            OutboundHttpRequest outboundHttpRequest = OutboundHttpRequest
                    .outboundRequest(this.host, this.port, this.contextPath, httpRequest);

            if (this.port == 443) {
                outboundHttpRequest.withSecure(true);
            }

            return this.nettyHttpClient.sendRequest(outboundHttpRequest);
        }
    }

    private static void initMockDynamoDB() {

        HttpRequest httpRequest;

        // mock User Preference table
        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.DeleteTable")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBDeleteTableRequest.json")));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBDeleteTableResponse.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.CreateTable")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBCreateTableRequest.json")));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBCreateTableResponse.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.DescribeTable")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBDescribeTableRequest.json")));

        mockServerClient.when(httpRequest, Times.exactly(1))
                .respond(response()
                        .withStatusCode(HttpStatus.SC_BAD_REQUEST)
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBDescribeTableBeenDeletedResponse.json"))));

        mockServerClient.when(httpRequest, Times.exactly(2))
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBDescribeTableResponse.json"))));

        // mock User Preference index table
        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.DeleteTable")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBDeleteIndexTableRequest.json")));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBDeleteIndexTableResponse.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.CreateTable")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBCreateIndexTableRequest.json")));

        mockServerClient.when(httpRequest)
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBCreateIndexTableResponse.json"))));

        httpRequest = request()
                .withMethod("POST")
                .withPath("/")
                .withHeader("X-Amz-Target", "DynamoDB_20120810.DescribeTable")
                .withBody(toJsonBody(new ClassPathResource("dynamoDBDescribeIndexTableRequest.json")));

        mockServerClient.when(httpRequest, Times.exactly(1))
                .respond(response()
                        .withStatusCode(HttpStatus.SC_BAD_REQUEST)
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBDescribeIndexTableBeenDeletedResponse.json"))));

        mockServerClient.when(httpRequest, Times.exactly(2))
                .respond(response()
                        .withStatusCode(HttpStatus.SC_OK)
                        .withHeader(new Header("Content-Type", "application/x-amz-json-1.0"))
                        .withBody(toJsonBody(new ClassPathResource("dynamoDBDescribeIndexTableResponse.json"))));
    }

}
