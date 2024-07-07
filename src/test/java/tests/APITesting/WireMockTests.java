package tests.APITesting;

import base.TestBase;
import io.qameta.allure.*;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class WireMockTests extends TestBase {

    @Test
    @Epic("User Management")
    @Feature("Retrieve Single User")
    @Story("Retrieve single user using mock")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifies that single user can be retrieved using mocked data.")
    public void testGetSingleUserMocked() {
        // Setup WireMock response
        wireMockServer.stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"name\": \"Leanne Graham\"}")));

        // Perform GET request and validate response
        given().spec(requestSpec)
                .when().get("/users/1")
                .then().statusCode(200)
                .body("name", equalTo("Leanne Graham"));
    }

    @Test
    @Epic("Post Management")
    @Feature("Create New Post")
    @Story("Create new post using mock")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that a new post can be created and the correct response is received.")
    public void testCreateNewPostMocked() {
        // Setup WireMock response for post creation
        wireMockServer.stubFor(post(urlEqualTo("/posts"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 101, \"title\": \"New Post\", \"body\": \"This is a new post.\", \"userId\": 1}")));

        // Perform POST request and validate response
        given().spec(requestSpec)
                .body("{\"title\": \"New Post\", \"body\": \"This is a new post.\", \"userId\": 1}")
                .when().post("/posts")
                .then().statusCode(201)
                .body("id", equalTo(101));
    }

    @Test
    @Epic("Error Handling")
    @Feature("Simulate Server Error")
    @Story("Simulate internal server error on user retrieval")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that the application correctly handles a 500 server error when trying to retrieve a user.")
    public void testInternalServerErrorSimulation() {
        // Setup WireMock response
        wireMockServer.stubFor(get(urlEqualTo("/users/2"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Internal Server Error\"}")));

        // Perform GET request and validate the response
        given().spec(requestSpec)
                .when().get("/users/2")
                .then().statusCode(500)
                .body("error", equalTo("Internal Server Error"));
    }

    @Test
    @Epic("Performance")
    @Feature("Simulate Slow Response")
    @Story("Simulate slow server response for a post retrieval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the application handles slow responses gracefully.")
    public void testSlowResponseSimulation() {
        // Setup WireMock response with a fixed delay
        wireMockServer.stubFor(get(urlEqualTo("/posts/50"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(5000)  // delay in milliseconds
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 50, \"title\": \"Delayed Post\"}")));

        // Perform GET request and validate response
        given().spec(requestSpec)
                .when().get("/posts/50")
                .then().statusCode(200)
                .body("title", equalTo("Delayed Post"));
    }

    @Test
    @Epic("Data Integrity")
    @Feature("Handle Incomplete Data")
    @Story("Handle incomplete data for a user profile")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the application handles incomplete data without crashing.")
    public void testIncompleteDataSimulation() {
        // Setup WireMock response with incomplete data
        wireMockServer.stubFor(get(urlEqualTo("/users/3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 3}")));  // Missing 'name' field

        // Perform GET request and validate response
        given().spec(requestSpec)
                .when().get("/users/3")
                .then().statusCode(200)
                .body("id", equalTo(3));
    }
}