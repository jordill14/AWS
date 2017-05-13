package org.paradise.microservice.userpreference.functional;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.matchers.Times;
import org.mockserver.model.Body;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.OutboundHttpRequest;
import org.mockserver.model.RegexBody;
import org.mockserver.model.StringBody;
import org.paradise.microservice.userpreference.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Base class for running Component tests using rest assured https://github.com/jayway/rest-assured
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = App.class)
@ActiveProfiles("local")
public abstract class AbstractFunctionalTest {

    protected static MockServerClient mockServerClient;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Value("${local.server.port}")
    private int port;


    @BeforeClass
    public static void beforeClass() {

        mockServerClient = startClientAndServer(8000);

        initMockDynamoDB();
    }

    /**
     * Individual functional test will overwrite this @Before method
     */
    @Before
    public void setUp() {

        // ONLY reset to the standard baseURI (localhost), basePath (empty), standard port (8080), default authentication
        // scheme (none) and default root path (empty string)
//        RestAssured.reset();

        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
        // Assign bound random port set in @SpringBootTest
        RestAssured.port = port;

        // This is important step for environment e.g. via Layer 7 API, which without it complaining
        // can't access PKI key store
        mockServerClient.retrieveExistingExpectations(request().withMethod("GET"));

        // This is a work around to stop the mocksever trust store getting in the way
        // This value will placed in the properties on the first request, however it is not used.
//        System.getProperties().remove("javax.net.ssl.trustStore");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @After
    public void tearDown() {
        mockServerClient.reset();
    }

    @AfterClass
    public static void afterClass() {
        mockServerClient.stop();
    }

    
    public static Body toJsonBody(Resource resource) {

        try {
            return new JsonBody(IOUtils.toString(resource.getInputStream(), Charset.defaultCharset()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Body toStringBody(Resource resource) {

        try {
            return new StringBody(IOUtils.toString(resource.getInputStream(), Charset.defaultCharset()));
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
