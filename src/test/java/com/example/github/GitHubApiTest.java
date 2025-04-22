package com.example.github;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GitHubApiTest {

    // Base URL for the GitHub API
    private static final String BASE_URL = "https://api.github.com";

    // GitHub username for which repositories will be fetched. Replace with your GitHub username if needed.
    private static final String GITHUB_USER = "YOUR_GITHUB_USERNAME";

    // GitHub token for authentication. Replace with your personal access token.
    private static final String GITHUB_TOKEN = "YOUR_GITHUB_TOKEN";

    // Setup method to initialize RestAssured's base URI before any tests are run.
    @BeforeClass
    public static void setup() {
        // Set the base URI for RestAssured to the GitHub API URL.
        RestAssured.baseURI = BASE_URL;
    }

    // Test method to verify the retrieval of repositories for a specific GitHub user.
    @Test
    public void testGetRepositoriesForUser() {
        // Make a GET request to the GitHub API to fetch repositories for the specified user.
        Response response = given()
                .header("Authorization", "Bearer " + GITHUB_TOKEN) // Add authorization header using the GitHub token.
                .when()
                .get("/users/" + GITHUB_USER + "/repos") // Endpoint to fetch repositories for the user.
                .then()
                .statusCode(200) // Assert that the response status code is 200 (OK).
                .contentType("application/json") // Assert that the response content type is JSON.
                .body("$", is(not(emptyArray()))) // Assert that the response body is not an empty array.
                .body("[0].name", notNullValue()) // Assert that the first repository's name is not null.
                .body("[0].id", greaterThan(0)) // Assert that the first repository's ID is greater than 0.
                .body("[0].owner.login", equalTo(GITHUB_USER)) // Assert that the owner's login matches the specified user.
                .extract().response(); // Extract the response object for further processing.

        // Print the response body in a pretty format to the console.
        response.body().prettyPrint();
    }
}
