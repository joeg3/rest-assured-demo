package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.restdemo.dto.PostDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * This demo tests against the fake api at:
 * https://jsonplaceholder.typicode.com/
 */
public class BasicTest {

  @BeforeClass
  public static void setBaseUri() {
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
  }

  @Test
  public void getTodoById() {

    Response response =
      given().
        contentType("application/json").
        when().
        get("/todos/1").
        then().
        statusCode(200).
        contentType(ContentType.JSON).
        body(("title"), equalTo("delectus aut autem")).
        extract().response();

    // Can check status code here in response or above in then()
    assertThat(response.statusCode()).isEqualTo(200);

    System.out.println("### ENTIRE RESPONSE ###");
    System.out.println(response.asString());

    System.out.println("### RESPONSE BODY ###");
    System.out.println(response.getBody().asString());

    System.out.println("### JSONPATH INDIVIDUAL VALUES ###");
    JsonPath jsonPathResponse = new JsonPath(response.asString());
    System.out.println("id: " + jsonPathResponse.get("id"));
    System.out.println("title: " + jsonPathResponse.get("title"));
    System.out.println("completed: " + jsonPathResponse.get("completed"));
    System.out.println("userId: " + jsonPathResponse.get("userId"));
  }

  @Test
  public void getTodoByTitleWithQueryParam() {

    Response response =
      given()
          .contentType(ContentType.JSON)
          .queryParam("title", "vero rerum temporibus dolor")
        .when()
          .get("/todos")
        .then()
          .statusCode(200)
          .contentType(ContentType.JSON)
          .body(("[0].id"), equalTo(11))
          .extract().response();

    System.out.println("### getTodoByTitleWithQueryParam ###");
    System.out.println(response.asString());
  }

  @Test
  public void getTodoByIdWithQueryParam() {

    Response response =
      given()
        .contentType(ContentType.JSON)
        .queryParam("id", 4)
        .when()
        .get("/todos")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body(("[0].title"), equalTo("et porro tempora"))
        .extract().response();

    System.out.println("### getTodoByIdWithQueryParam ###");
    System.out.println(response.asString());
  }

  @Test
  public void getTodoByIdWithPathParam() {

    Response response =
      given()
        .contentType(ContentType.JSON)
        .pathParams("todoId", "4")
        .when()
        .get("/todos/{todoId}")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body(("title"), equalTo("et porro tempora")) // Unlike query param that returns array, path param returns single todo
        .extract().response();

    System.out.println("### getTodoByIdWithPathParam ###");
    System.out.println(response.asString());
  }

  //@Test
  public void postExample() {

    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    Response response =
      given().
        contentType("application/json").
        body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }").
        when().
        post("/posts").
        then().
        statusCode(201).
        //and.
          body("title", equalTo("foo")).
        extract().response();

    JsonPath jsonPathResponse = new JsonPath(response.asString());
    System.out.println("### POST ###");

    System.out.println("### RESPONSE ###");
    System.out.println(response.getBody().asString());

    System.out.println("### JSONPATH INDIVIDUAL VALUES ###");
    System.out.println("id: " + jsonPathResponse.get("id"));
    System.out.println("title: " + jsonPathResponse.get("title"));
    System.out.println("body: " + jsonPathResponse.get("body"));
    System.out.println("userId: " + jsonPathResponse.get("userId"));

  }

}
