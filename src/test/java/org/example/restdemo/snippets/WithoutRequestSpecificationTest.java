package org.example.restdemo.snippets;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * This is an example of running Rest Assured without a RequestSpecification
 * Generally you would not want to do this since you would have setup
 * code in each testcase.
 *
 * This demo tests against the fake api at:
 * https://jsonplaceholder.typicode.com/
 */
public class WithoutRequestSpecificationTest {

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
