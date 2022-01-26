package org.example.restdemo;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class uses Rest Assured where we have set up
 * RequestSpecification and ResponseSpecification objects.
 * This is a best practice so validation of things like
 * Content Type and Status Code are in one place and can be reused.
 */
public class SpecificationExamples {
    private static RequestSpecification reqSpec;
    private ResponseSpecification resSpec;

    /**
     * We use the same Request Specification for all tests in
     * this class, so we can just run it once and set the
     * static variable
     */
    @BeforeAll
    static void beforeAllTests() {
        reqSpec = RestBaseTest.createRequestSpec();
    }

    /**
     * We use the same Response Specification for all tests in
     * this class except one, createTodo(). This is because a status code
     * of 200 is returned for all tests except creating a todo which returns
     * a 201 when successful. So we'll set the Response Specification for
     * each test case, and in createTodo(), we'll create a custom Response Specification
     */
    @BeforeEach
    void beforeEachTest() {
        resSpec = RestBaseTest.createResponseSpec();
    }

    @Test
    void getAllTodos() {
        JsonPath response =
            given().
                spec(reqSpec).
            when().
                get("/todos").
            then().
                spec(resSpec).
                extract().jsonPath();

        assertThat(response.getInt("[0].id")).isEqualTo(1); // First item in array has id equal to 1
        assertThat(response.getList("$").size()).isGreaterThan(100); // Since array is root element, we use special character '$' to reference it
        assertThat(response.getList("$")).doesNotContainNull();  // The id of every item in root array should have a value
        assertThat(response.getInt("[0].id")).isEqualTo(1);
    }

    @Test
    void getOneTodo() {
        JsonPath response =
            given().
                spec(reqSpec).
            when().
                get("/todos/2").
            then().
                spec(resSpec).
                extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(2);
        assertThat(response.getInt("userId")).isEqualTo(1);
        assertThat(response.getString("title")).isEqualTo("quis ut nam facilis et officia qui");
        assertThat(response.getBoolean("completed")).isFalse();
    }

    @Test
    void createTodo() {
        // We'll create a ResponseSpecification for this test case because unlike all the others, it
        // returns a status code of 201 instead of 200
        resSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();

        String body = " { \"userId\": 1, \"title\": \"Walk Dog\", \"completed\": false }";

        JsonPath response =
            given().
                spec(reqSpec).
                body(body).
            when().
                post("/todos").
            then().
                spec(resSpec).
                extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(201);
    }

    @Test
    void updateTodo() {
        String body = " { \"id\": 2, \"userId\": 1, \"title\": \"Nap\", \"completed\": true }";

        JsonPath response =
            given().
                spec(reqSpec).
                body(body).
            when().
                put("/todos/2").
            then().
                spec(resSpec).
                extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(2);
    }

    @Test
    void deleteTodo() {
        JsonPath response =
            given().
                spec(reqSpec).
            when().
                delete("/todos/2").
            then().
                spec(resSpec).
                extract().jsonPath();
        // API doesn't return any data for deletes, just: { }
        // I don't think this API we are testing against actually deletes the item
        // But normally we would look at the response for confirmation of the delete
        // And we could also query for the id we just deleted to verify it's no longer there
    }


}
