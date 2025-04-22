package com.example.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JsonPlaceholderApiTest {

    // Base URL for the JSONPlaceholder API
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";

    // Setup method to initialize RestAssured's base URI before any tests are run.
    @BeforeClass
    public static void setup() {
        // Set the base URI for RestAssured to the JSONPlaceholder API URL.
        RestAssured.baseURI = BASE_URL;
    }

    // Utility method to print all response headers in a readable format.
    private void printResponseHeaders(Response response) {
        System.out.println("Response Headers:");
        response.getHeaders().forEach(header -> {
            System.out.printf("%s: %s%n", header.getName(), header.getValue());
        });
    }

    // Utility method to print the response body in a readable format.
    private void printResponseBody(Response response) {
        response.body().prettyPrint();
    }

    // Test method to verify the retrieval of a post by ID.
    @Test
    public void testGetPostById() {
        // Make a GET request to the JSONPlaceholder API to fetch a post by ID.
        Response response = given()
                .when()
                .get("/posts/1") // Endpoint to fetch a post by ID.
                .then()
                .statusCode(200) // Assert that the response status code is 200 (OK).
                .contentType("application/json") // Assert that the response content type is JSON.
                .body("id", equalTo(1)) // Assert that the post ID is 1.
                .body("title", notNullValue()) // Assert that the post title is not null.
                .body("body", notNullValue()) // Assert that the post body is not null.
                .body("userId", greaterThan(0)) // Assert that the user ID is greater than 0.
                .body("title", matchesPattern("sunt aut facere.*")) // Assert that the title matches the given pattern.
                .extract().response(); // Extract the response object for further processing.

        // Print the response headers and body in a readable format.
        printResponseHeaders(response);
        printResponseBody(response);
    }

    // Test method to verify the creation of a new post.
    @Test
    public void testCreatePost() {
        // Define the new post data.
        Map<String, Object> newPost = Map.of(
                "title", "test title",
                "body", "test body",
                "userId", 1
        );

        // Define the expected JSON body.
        Map<String, Object> expectedJsonBody = Map.of(
                "title", "test title",
                "userId", 1,
                "body", "test body",
                "id", 101
        );

        // Make a POST request to the JSONPlaceholder API to create a new post.
        Response response = given()
                .contentType("application/json") // Set the content type to JSON.
                .body(newPost) // Set the request body to the new post data.
                .when()
                .post("/posts") // Endpoint to create a new post.
                .then()
                .statusCode(201) // Assert that the response status code is 201 (Created).
                .body("title", equalTo("test title")) // Assert that the post title is "test title".
                .body("body", equalTo("test body")) // Assert that the post body is "test body".
                .body("userId", equalTo(1)) // Assert that the user ID is 1.
                .body("id", greaterThan(0)) // Assert that the post ID is greater than 0.
                .body("", equalTo(expectedJsonBody)) //Assert that the whole post is the expected JSON body
                .extract().response(); // Extract the response object for further processing.

        // Print the response headers and body in a readable format.
        printResponseHeaders(response);
        printResponseBody(response);
    }

    // Test method to verify the update of an existing post.
    @Test
    public void testUpdatePost() {
        // Define the updated post data.
        Map<String, Object> updatedPost = Map.of(
                "id", 1,
                "title", "updated title",
                "body", "updated body",
                "userId", 1
        );

        // Make a PUT request to the JSONPlaceholder API to update an existing post.
        Response response = given()
                .contentType("application/json") // Set the content type to JSON.
                .body(updatedPost) // Set the request body to the updated post data.
                .when()
                .put("/posts/1") // Endpoint to update a post by ID.
                .then()
                .statusCode(200) // Assert that the response status code is 200 (OK).
                .body("title", equalTo("updated title")) // Assert that the post title is "updated title".
                .body("body", equalTo("updated body")) // Assert that the post body is "updated body".
                .body("id", equalTo(1)) // Assert that the post ID is 1.
                .body("userId", equalTo(1)) // Assert that the user ID is 1.
                .extract().response(); // Extract the response object for further processing.

        // Print the response headers and body in a readable format.
        printResponseHeaders(response);
        printResponseBody(response);
    }

    // Test method to verify the partial update of an existing post.
    @Test
    public void testPatchPost() {
        // Define the partial update data.
        Map<String, Object> partialUpdate = Map.of(
                "title", "patched title"
        );

        // Make a PATCH request to the JSONPlaceholder API to partially update an existing post.
        Response response = given()
                .contentType("application/json") // Set the content type to JSON.
                .body(partialUpdate) // Set the request body to the partial update data.
                .when()
                .patch("/posts/1") // Endpoint to partially update a post by ID.
                .then()
                .statusCode(200) // Assert that the response status code is 200 (OK).
                .body("title", equalTo("patched title")) // Assert that the post title is "patched title".
                .body("id", equalTo(1)) // Assert that the post ID is 1.
                .body("userId", greaterThan(0)) // Assert that the user ID is greater than 0.
                .extract().response(); // Extract the response object for further processing.

        // Print the response headers and body in a readable format.
        printResponseHeaders(response);
        printResponseBody(response);
    }

    // Test method to verify the deletion of an existing post.
    @Test
    public void testDeletePost() {
        // Make a DELETE request to the JSONPlaceholder API to delete an existing post.
        Response response = given()
                .when()
                .delete("/posts/1") // Endpoint to delete a post by ID.
                .then()
                .statusCode(200) // Assert that the response status code is 200 (OK).
                .extract().response(); // Extract the response object for further processing.

        // Print the response headers and body in a readable format.
        printResponseHeaders(response);
        printResponseBody(response);
    }

    // Test method to verify the HEAD request for an existing post.
    @Test
    public void testHeadPost() {
        // Make a HEAD request to the JSONPlaceholder API to get headers for an existing post.
        Response response = given()
                .when()
                .head("/posts/1") // Endpoint to get headers for a post by ID.
                .then()
                .statusCode(200) // Assert that the response status code is 200 (OK).
                .header("Content-Type", containsString("application/json")) // Assert that the Content-Type header contains "application/json".
                .extract().response(); // Extract the response object for further processing.

        // Print the response headers in a readable format.
        printResponseHeaders(response);
    }
}
