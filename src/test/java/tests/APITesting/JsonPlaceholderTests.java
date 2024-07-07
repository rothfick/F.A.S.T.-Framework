package tests.APITesting;

import base.TestBase;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonPlaceholderTests extends TestBase {

    @Test
    @Epic("Post Management")
    @Feature("Retrieve Posts")
    @Story("Retrieve all posts")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that all posts can be retrieved and the status code is 200.")
    public void testGetPosts() {
        requestSpec
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    @Epic("Comments Management")
    @Feature("Retrieve Comments")
    @Story("Retrieve comments for a specific post")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that comments for a specific post can be retrieved and the status code is 200.")
    public void testGetCommentsForPost() {
        int postId = 1;
        requestSpec
                .when()
                .get("/posts/" + postId + "/comments")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    @Epic("User Management")
    @Feature("Retrieve Users")
    @Story("Retrieve a single user")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a single user can be retrieved and the status code is 200.")
    public void testGetUser() {
        int userId = 1;
        requestSpec
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    @Epic("Photo Management")
    @Feature("Retrieve Photos")
    @Story("Retrieve all photos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that all photos can be retrieved and the status code is 200.")
    public void testGetPhotos() {
        requestSpec
                .when()
                .get("/photos")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    @Epic("Post Management")
    @Feature("Update Post")
    @Story("Update an existing post")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that an existing post can be updated and the status code is 200.")
    public void testUpdatePost() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("title", "updated title");

        requestSpec
                .body(jsonAsMap)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    @Epic("Post Management")
    @Feature("Delete Post")
    @Story("Delete an existing post")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that an existing post can be deleted and the status code is 200.")
    public void testDeletePost() {
        requestSpec
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Epic("Post Management")
    @Feature("Create Post")
    @Story("Create a new post")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a new post can be created and the status code is 201.")
    public void testCreatePost() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("title", "New Post");
        jsonAsMap.put("body", "This is a new post.");
        jsonAsMap.put("userId", 1);

        requestSpec
                .body(jsonAsMap)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .log().body();
    }

    @Test
    @Epic("User Management")
    @Feature("Retrieve User Posts")
    @Story("Retrieve posts by a specific user")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that posts by a specific user can be retrieved and their count matches expected.")
    public void testGetPostsByUser() {
        int userId = 1;
        requestSpec
                .when()
                .get("/users/" + userId + "/posts")
                .then()
                .statusCode(200)
                .body("size()", is(10));
    }

    @Test
    @Epic("Post Management")
    @Feature("Post-Comment Consistency")
    @Story("Verify post title and fetch its comments")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a post's title is not null and its comments can be successfully retrieved.")
    public void testPostAndItsComments() {
        int postId = 1;
        JsonPath postResponse = requestSpec
                .when()
                .get("/posts/" + postId)
                .then()
                .extract().jsonPath();

        String postTitle = postResponse.getString("title");
        assert postTitle != null;

        requestSpec
                .when()
                .get("/posts/" + postId + "/comments")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .log().body();
    }

    @Test
    @Epic("User Management")
    @Feature("Post-User Consistency")
    @Story("Verify consistency of user posts")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that all posts retrieved for users match the expected user IDs.")
    public void testUserPostsConsistency() {
        JsonPath usersResponse = requestSpec
                .when()
                .get("/users")
                .then()
                .extract().jsonPath();

        List<Integer> userIds = usersResponse.getList("id");

        for (Integer id : userIds) {
            requestSpec
                    .when()
                    .get("/users/" + id + "/posts")
                    .then()
                    .statusCode(200)
                    .assertThat()
                    .body("userId", everyItem(equalTo(id)));
        }
    }

    @Test
    @Epic("Error Handling")
    @Feature("Client Errors")
    @Story("Test non-existing post retrieval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that retrieving a non-existing post returns a 404 status code.")
    public void testGetNonExistingPost() {
        requestSpec
                .when()
                .get("/posts/9999")
                .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    @Epic("Server Error Handling")
    @Feature("Internal Server Error")
    @Story("Check handling of non-existing resources")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that accessing a non-existing resource returns a 404 Not Found status, as JSONPlaceholder cannot simulate a 500 Internal Server Error.")
    public void testInternalServerError() {
        requestSpec
                .when()
                .get("/unstable-resource")  // This endpoint does not exist
                .then()
                .statusCode(404)  // We expect a 404 Not Found instead of 500 Internal Server Error
                .log().body();
    }

}
