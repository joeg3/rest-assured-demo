package org.example.graphqldemo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GraphqlTest {
  private static  RequestSpecification reqSpec;
  private static  ResponseSpecification resSpec;

  @BeforeAll
  static void beforeAllTests() {
    reqSpec = GraphqlBaseTest.createRequestSpec();
    resSpec = GraphqlBaseTest.createResponseSpec();
  }

  @Test
  void firstTry() {
    String query = "{ viewer { name url } }";
    String graphQL =" { \"query\": \"query { viewer { login }}\" }";
    graphQL = String.format("{ \"query\": \"query %s\" }", query);

    JsonPath jsonPath =
      given()
        .spec(reqSpec)
        .body(graphQL)
        .when()
        .post("")
        .then()
        //.spec(resSpec)
        .log().body()
        .extract().jsonPath();

    jsonPath.prettyPrint();

  }

}
