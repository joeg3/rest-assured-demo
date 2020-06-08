package org.example.restdemo.snippets;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.restdemo.RestBaseTest;
import org.example.restdemo.dto.TodoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * This shows what you can do with the response
 * from a Rest-Assured test
 *
 * This demo tests against the fake api at:
 * https://jsonplaceholder.typicode.com/
 */
public class ResponseExamplesTest {
  private static  RequestSpecification reqSpec;
  private static ResponseSpecification resSpec;

  @BeforeAll
  static void beforeAllTests() {
    reqSpec = RestBaseTest.createRequestSpec();
    resSpec = RestBaseTest.createResponseSpec();
  }

  @Test
  void getResponse() {

    Response response =
      given()
        .spec(reqSpec)
        .when()
        .get("/todos/1")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().response();

    // Can check parts of response here or above in then()
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.contentType()).isEqualTo("application/json; charset=utf-8");
    assertThat(response.getCookies().size()).isEqualTo(1);

    System.out.println("### Test: getResponse() ###");

    System.out.println("### ENTIRE RESPONSE ###");
    System.out.println(response.asString());

    System.out.println("### RESPONSE BODY ###");
    System.out.println(response.getBody().asString());

    System.out.println("HEADER NAME   |   HEADER VALUE");
    Headers headers = response.getHeaders();
    for (Header header : headers) {
      System.out.println(header.getName() + "   |   " + header.getValue());
    }
    System.out.println("### End of Header Values ###");
  }

  @Test
  void getJsonPathSingleEntity() {

    JsonPath retrievedTodo =
      given()
        .spec(reqSpec)
        .when()
        .get("/todos/1")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().jsonPath();

    // If you extract the response instead of jsonpath, you can still get jsonpath from response
    // JsonPath jsonPathResponse = new JsonPath(response.asString());

    assertThat(retrievedTodo.getInt("id")).isEqualTo(1);
    assertThat(retrievedTodo.getString("title")).isEqualTo("delectus aut autem");
    assertThat(retrievedTodo.getBoolean("completed")).isFalse();
    assertThat(retrievedTodo.getInt("userId")).isEqualTo(1);
  }

  @Test
  void getJsonPathMultipleEntities() {

    JsonPath retrievedTodos =
      given()
        .spec(reqSpec)
        .queryParam("userId", 1)
        .when()
        .get("/todos")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().jsonPath();

    assertThat(retrievedTodos.getList("").size()).isGreaterThan(10);
  }

  @Test
  void verifyResultsInRestAssuredCode() {

    JsonPath retrievedTodo =
      given()
        .spec(reqSpec)
        .when()
        .get("/todos/1")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body(("id"), equalTo(1))
        .body(("title"), equalTo("delectus aut autem"))
        .body(("completed"), equalTo(false))
        .body(("userId"), equalTo(1))
        .extract().jsonPath();
  }

  @Test
  void getPojo() {

    TodoDTO retrievedTodo =
      given()
        .spec(reqSpec)
        .when()
        .get("/todos/1")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().as(TodoDTO.class);

    assertThat(retrievedTodo.getId()).isEqualTo(1);
    assertThat(retrievedTodo.getTitle()).isEqualTo("delectus aut autem");
    assertThat(retrievedTodo.getCompleted()).isFalse();
    assertThat(retrievedTodo.getUserId()).isEqualTo(1);
  }
}
