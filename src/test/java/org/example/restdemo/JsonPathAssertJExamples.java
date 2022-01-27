package org.example.restdemo;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class uses Rest Assured where we have it return
 * a JsonPath object and then use AssertJ to verify
 * the result.
 */
public class JsonPathAssertJExamples {

    @Test
    void getAllTodos() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        JsonPath response =
            given().
            when().
                get(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                extract().jsonPath();

        assertThat(response.getInt("[0].id")).isEqualTo(1); // First item in array has id equal to 1
        assertThat(response.getList("$").size()).isGreaterThan(100); // Since array is root element, we use special character '$' to reference it
        assertThat(response.getList("$")).doesNotContainNull();  // The id of every item in root array should have a value
        assertThat(response.getInt("[0].id")).isEqualTo(1);
    }

    @Test
    void getOneTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        JsonPath response =
            given().
            when().
                get(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(2);
        assertThat(response.getInt("userId")).isEqualTo(1);
        assertThat(response.getString("title")).isEqualTo("quis ut nam facilis et officia qui");
        assertThat(response.getBoolean("completed")).isFalse();
    }

    @Test
    void createTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        String body = " { \"userId\": 1, \"title\": \"Walk Dog\", \"completed\": false }";

        JsonPath response =
            given().
                body(body).
            when().
                post(endpoint).
            then().
                statusCode(201).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(201);
    }

    @Test
    void updateTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        String body = " { \"id\": 2, \"userId\": 1, \"title\": \"Nap\", \"completed\": true }";

        JsonPath response =
            given().
                body(body).
            when().
                put(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(2);
    }

    @Test
    void deleteTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2"; // Delete id 2

        JsonPath response =
            given().
            when().
                delete(endpoint).
            then().
                statusCode(200).
                contentType(ContentType.JSON). // Two different ways to check content type
                header("Content-Type", "application/json; charset=utf-8").
                extract().jsonPath();
        // API doesn't return any data for deletes, just: { }
        // I don't think this API we are testing against actually deletes the item
        // But normally we would look at the response for confirmation of the delete
        // And we could also query for the id we just deleted to verify it's no longer there
    }
}
