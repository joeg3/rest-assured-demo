package org.example.restdemo;

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
 * This is an example of running Rest Assured with a RequestSpecification
 * Generally you would want to do this so the setup code is in one place.
 *
 * This demo tests against the fake api at:
 * https://jsonplaceholder.typicode.com/
 */
public class WithRequestSpecificationTest {
  private static RequestSpecification spec;

  @BeforeClass
  public static void initSpec(){
    spec = new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .setBaseUri("https://jsonplaceholder.typicode.com")
      // Here are some more options:
      //   .addHeader("headerName","headerValue")
      //   .addCookie(new Cookie.Builder("name", "value").setPath("...").setDomain("...").build())
      //   .addFilter(new ResponseLoggingFilter()) // Log request and response for better debugging
      //   .addFilter(new RequestLoggingFilter())  // You can also only log if a requests fails
      .build();
  }

  @Test
  public void getTodoById() {

    Response response =
      given().
        spec(spec).
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
