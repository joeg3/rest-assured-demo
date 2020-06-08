package org.example.restdemo.snippets;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.restdemo.RestBaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * This is an example of running Rest Assured with a RequestSpecification
 * Generally you would want to do this so the setup code is in one place.
 *
 * This demo tests against the fake api at:
 * https://jsonplaceholder.typicode.com/
 */
public class WithRequestSpecificationTest {
  private static  RequestSpecification reqSpec;
  private static ResponseSpecification resSpec;

  @BeforeAll
  static void beforeAllTests() {
    reqSpec = RestBaseTest.createRequestSpec();
    resSpec = RestBaseTest.createResponseSpec();
  }

  @Test
  void getTodoById() {

    Response response =
      given().
        spec(reqSpec).
        when().
        get("/todos/1").
        then().
        statusCode(200).
        contentType(ContentType.JSON).
        body(("title"), equalTo("delectus aut autem")).
        extract().response();

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


}
