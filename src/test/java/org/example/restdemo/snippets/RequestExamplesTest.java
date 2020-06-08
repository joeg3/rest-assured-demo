package org.example.restdemo.snippets;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.restdemo.RestBaseTest;
import org.example.restdemo.dto.TodoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * This shows how you can make requests with query parameters, or post parameters
 *
 * This demo tests against the fake api at: https://jsonplaceholder.typicode.com/
 */
public class RequestExamplesTest {
  private static  RequestSpecification reqSpec;
  private static ResponseSpecification resSpec;

  @BeforeAll
  static void beforeAllTests() {
    reqSpec = RestBaseTest.createRequestSpec();
    resSpec = RestBaseTest.createResponseSpec();
  }

  @Test
  void getTodoByIdInPath() {

    Response response =
      given()
        .spec(reqSpec)
        .when()
        .get("/todos/1")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body(("title"), equalTo("delectus aut autem"))
        .extract().response();

    System.out.println("### ENTIRE RESPONSE ###");
    System.out.println(response.asString());

    System.out.println("### RESPONSE BODY ###");
    System.out.println(response.getBody().asString());

    System.out.println("### getTodoByIdInPath ###");
    JsonPath jsonPathResponse = new JsonPath(response.asString());
    System.out.println("id: " + jsonPathResponse.get("id"));
    System.out.println("title: " + jsonPathResponse.get("title"));
    System.out.println("completed: " + jsonPathResponse.get("completed"));
    System.out.println("userId: " + jsonPathResponse.get("userId"));
  }

  @Test
  void getTodoByTitleWithQueryParam() {

    Response response =
      given()
        .spec(reqSpec)
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
  void getTodoByIdWithQueryParam() {

    Response response =
      given()
        .spec(reqSpec)
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
  void getTodoByIdWithPathParam() {

    Response response =
      given()
        .spec(reqSpec)
        .pathParams("todoId", "4")
        .when()
        .get("/todos/{todoId}")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body(("title"), equalTo("et porro tempora")) // Unlike query param that returns array, path param returns single item
        .extract().response();

    System.out.println("### getTodoByIdWithPathParam ###");
    System.out.println(response.asString());
  }

  @Test
  void createTodoWithStringInBody() {

    Response response =
      given()
        .spec(reqSpec)
        .body("{ \"title\": \"foo\", \"completed\": false, \"userId\": 1 }")
        .when()
        .post("/todos")
        .then()
        .statusCode(201)
        .body("title", equalTo("foo"))
        .extract().response();

    JsonPath jsonPathResponse = new JsonPath(response.asString());

    System.out.println("### createTodoWithStringInBody ###");
    System.out.println(response.getBody().asString());

    System.out.println("### createTodoWithStringInBody ###");
    System.out.println("id: " + jsonPathResponse.get("id"));
    System.out.println("title: " + jsonPathResponse.get("title"));
    System.out.println("completed: " + jsonPathResponse.get("completed"));
    System.out.println("userId: " + jsonPathResponse.get("userId"));

  }

  @Test
  void createTodoWithPojoInBody() {
    TodoDTO newTodo = new TodoDTO()
      .setTitle("Walk dog")
      .setCompleted(false)
      .setUserId(1);

    Response response =
      given()
        .spec(reqSpec)
        .body(newTodo)
        .when()
        .post("/todos")
        .then()
        .statusCode(201)
        .body("title", equalTo("Walk dog"))
        .extract().response();

    JsonPath jsonPathResponse = new JsonPath(response.asString());

    System.out.println("### createTodoWithPojoInBody ###");
    System.out.println(response.getBody().asString());

    System.out.println("### createTodoWithPojoInBody ###");
    System.out.println("id: " + jsonPathResponse.get("id"));
    System.out.println("title: " + jsonPathResponse.get("title"));
    System.out.println("completed: " + jsonPathResponse.get("completed"));
    System.out.println("userId: " + jsonPathResponse.get("userId"));

  }

}
