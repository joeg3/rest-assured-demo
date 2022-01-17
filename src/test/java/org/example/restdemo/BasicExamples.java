package org.example.restdemo;

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
        var response = given().when().get(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                body("[0].id", equalTo(1)).  // First item in array has id equal to 1
                        body("$.size()", greaterThan(100)). // Since array is root element, we use special character '$' to reference it
                        body("$.id", everyItem(notNullValue())).  // The id of every item in root array should have a value
                        body("[99].id", equalTo(100));
        response.log().body(); // Print body of response to stdout
    }

    @Test
    void getOneTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        var response = given().when().get(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                body("id", equalTo(2)).
                body("userId", equalTo(1)).
                body("title", equalTo("quis ut nam facilis et officia qui")).
                body("completed", equalTo(false));

        response.log().body(); // Print body of response to stdout
    }

    @Test
    void createTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        String body = " { \"userId\": 1, \"title\": \"Walk Dog\", \"completed\": false }";

        var response = given().body(body).when().post(endpoint).then().
                assertThat().
                statusCode(201).
                header("Content-Type", "application/json; charset=utf-8").
                body("id", equalTo(201));
    }

    @Test
    void updateTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        String body = " { \"id\": 2, \"userId\": 1, \"title\": \"Nap\", \"completed\": true }";

        var response = given().body(body).when().put(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                body("id", equalTo(2));
        response.log().body(); // Print body of response to stdout
    }

    @Test
    void deleteTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";

        var response = given().when().delete(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8");
        response.log().body(); // Print body of response to stdout
    }


}
