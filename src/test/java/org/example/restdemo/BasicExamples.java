package org.example.restdemo;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

/**
 * This class uses Rest Assured in the most basic way possible.
 * The Hamcrest assertion library that comes with Rest Assured
 * is used for assertions.
 */
public class BasicExamples {

    @Test
    void getAllTodos() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        var response =
            given().
                contentType(ContentType.JSON). // Sending JSON data
            when().
                get(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                body("[0].id", equalTo(1)).  // First item in array has id equal to 1
                body("$.size()", greaterThan(100)). // Since array is root element, we use special character '$' to reference it
                body("$.id", everyItem(notNullValue())).  // The id of every item in root array should have a value
                body("[99].id", equalTo(100));

        // response.log().body(); // Print body of response to stdout
    }

    @Test
    void getTodoByIdInPath() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        var response =
            given().
                contentType(ContentType.JSON). // Sending JSON data
            when().
                get(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                body("id", equalTo(2)).
                body("userId", equalTo(1)).
                body("title", equalTo("quis ut nam facilis et officia qui")).
                body("completed", equalTo(false));

        // response.log().body(); // Print body of response to stdout
    }

    @Test
    void createTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        String body = " { \"userId\": 1, \"title\": \"Walk Dog\", \"completed\": false }";

        var response =
            given().
                body(body).
                contentType(ContentType.JSON). // Sending JSON data
            when().
                post(endpoint).
            then().
                statusCode(201).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                body("id", equalTo(201));

        // response.log().body(); // Print body of response to stdout
    }

    @Test
    void updateTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        String body = " { \"id\": 2, \"userId\": 1, \"title\": \"Nap\", \"completed\": true }";

        var response =
                given().
                    body(body).
                    contentType(ContentType.JSON). // Sending JSON data
                when().
                    put(endpoint).
                then().
                    statusCode(200).
                    contentType(ContentType.JSON). // Two different ways to check content type
                    header("Content-Type", "application/json; charset=utf-8").
                    body("id", equalTo(2));

        // response.log().body(); // Print body of response to stdout
    }

    @Test
    void deleteTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2"; // Delete id 2

        var response =
            given().
                contentType(ContentType.JSON). // Sending JSON data
            when().
                delete(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8");

        // API doesn't return any data for deletes, just: { }
        // I don't think this API we are testing against actually deletes the item
        // But normally we would look at the response for confirmation of the delete
        // And we could also query for the id we just deleted to verify it's no longer there

        // response.log().body(); // Print body of response to stdout
    }
}
