package org.example.restdemo;

import io.restassured.common.mapper.TypeRef;
import org.example.restdemo.models.Todo;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * This class uses serialized classes with Rest Assured
 * The Hamcrest assertion library that comes with Rest Assured
 * is used for assertions.
 */
public class SerializedExamples {

    @Test
    void getAllTodos() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        Todo [] todos = given().
                when().
                get(endpoint).
                then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                extract().as(Todo[].class);
        assertThat(todos, arrayWithSize(200));
        assertThat(todos[99].getId(), equalTo(100));
        assertThat(todos, not(emptyArray()));
        assertThat(Arrays.asList(todos), everyItem(notNullValue()));
    }

    @Test
    void getOneTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2"; // This gets Todo with id = 2
        Todo expectedTodo = new Todo(2, 1, "quis ut nam facilis et officia qui", false);
        Todo actualTodo = given().when().get(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                extract().as(Todo.class);

        assertThat(actualTodo, samePropertyValuesAs(expectedTodo));
    }

    @Test
    void createTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos";
        Todo todoPayload = new Todo(1, "Walk Dog", false);
        Todo expectedTodo = new Todo();
        expectedTodo.setId(201);

        Todo actualTodo = given().body(todoPayload).when().post(endpoint).then().
                assertThat().
                statusCode(201).
                header("Content-Type", "application/json; charset=utf-8").
                extract().as(Todo.class);

        assertThat(actualTodo, samePropertyValuesAs(expectedTodo));
    }

    @Test
    void updateTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        Todo expectedTodo = new Todo();
        expectedTodo.setId(2);
        Todo payloadTodo = new Todo( 2, 1, "nap", true);

        Todo actualTodo = given().body(payloadTodo).when().put(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                extract().as(Todo.class);

        assertThat(actualTodo, samePropertyValuesAs(expectedTodo));
    }

    @Test
    void deleteTodo() {
        String endpoint = "https://jsonplaceholder.typicode.com/todos/2";
        Todo expectedTodo = new Todo(); // API doesn't return any data for deletes

        Todo actualTodo = given().when().delete(endpoint).then().
                assertThat().
                statusCode(200).
                header("Content-Type", "application/json; charset=utf-8").
                extract().as(Todo.class);

        assertThat(actualTodo, samePropertyValuesAs(expectedTodo));
    }


}
