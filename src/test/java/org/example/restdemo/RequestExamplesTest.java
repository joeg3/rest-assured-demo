package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.restdemo.dto.TodoDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * This shows how you can make requests with
 * query parameters, or post parameters
 *
 * This demo tests against the fake api at:
 * https://jsonplaceholder.typicode.com/
 */
public class RequestExamplesTest {
  private static RequestSpecification spec;

  @BeforeClass
  public static void initSpec(){
    spec = new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .setBaseUri("https://jsonplaceholder.typicode.com")
      .build();
  }

  @Test
  public void getTodoByIdInPath() {

    Response response =
      given()
        .spec(spec)
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
  public void getTodoByTitleWithQueryParam() {

    Response response =
      given()
        .spec(spec)
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
        .spec(spec)
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
        .spec(spec)
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
  public void createTodoWithStringInBody() {

    Response response =
      given()
        .spec(spec)
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
  public void createTodoWithPojoInBody() {
    TodoDTO newTodo = new TodoDTO()
      .setTitle("Walk dog")
      .setCompleted(false)
      .setUserId(1);

    Response response =
      given()
        .spec(spec)
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
