package org.example.restdemo;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class shows additional ways to pass parameters in
 * the request, and how to retrieve headers from the response.
 */
public class HeaderAndParameterExamples {
    private static RequestSpecification reqSpec;
    private static ResponseSpecification resSpec;

    @BeforeAll
    static void beforeAllTests() {
        reqSpec = BaseTest.createRequestSpec();
        resSpec = BaseTest.createResponseSpec();
    }

    @Test
    void setHeaderValues() {
        Headers headers =
                given().
                    header("headerName","headerValue").
                    spec(reqSpec).
                when().
                    get("/todos/2").
                then().
                    spec(resSpec).
                    extract().headers();

        assertThat(headers.getValue("Content-Type")).isEqualTo("application/json; charset=utf-8");
    }

    @Test
    void checkHeaderValues() {
        Headers headers =
                given().
                    spec(reqSpec).
                when().
                    get("/todos/2").
                then().
                    spec(resSpec).
                    extract().headers();

        for (Header h: headers) {
            //System.out.println("Header: " + h);
        }
        assertThat(headers.getValue("Content-Type")).isEqualTo("application/json; charset=utf-8");
    }

    @Test
    void getTodoByIdInPath() {
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
    void getTodoByTitleWithQueryParam() {
        JsonPath response =
                given().
                    spec(reqSpec).
                    queryParam("title", "quis ut nam facilis et officia qui").
                when().
                    get("/todos").
                then().
                    spec(resSpec).
                    extract().jsonPath();

        // Assume query just returns array of the one item we are looking for
        assertThat(response.getInt("[0].id")).isEqualTo(2);
        assertThat(response.getInt("[0].userId")).isEqualTo(1);
        assertThat(response.getString("[0].title")).isEqualTo("quis ut nam facilis et officia qui");
        assertThat(response.getBoolean("[0].completed")).isFalse();
    }

    @Test
    void getTodoByIdWithPathParam() {
        JsonPath response =
                given().
                    spec(reqSpec).
                    pathParams("todoId", "2").
                when().
                        get("/todos/{todoId}").
                        then().
                        spec(resSpec).
                        extract().jsonPath();

        assertThat(response.getInt("id")).isEqualTo(2);
        assertThat(response.getInt("userId")).isEqualTo(1);
        assertThat(response.getString("title")).isEqualTo("quis ut nam facilis et officia qui");
        assertThat(response.getBoolean("completed")).isFalse();
    }
}
