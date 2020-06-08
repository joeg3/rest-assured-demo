package org.example.graphqldemo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GraphqlBaseTest {
  public static final String GITHUB_TOKEN = System.getProperty("github.token");
  public static String BASE_URI = "https://api.github.com/graphql";

  public static RequestSpecification createRequestSpec() {
    return new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .addHeader("Authorization", "bearer " + GITHUB_TOKEN)
      .setBaseUri(BASE_URI)
      .build();
  }

  public static ResponseSpecification createResponseSpec() {
    return new ResponseSpecBuilder()
      .expectStatusCode(200)
      .expectContentType(ContentType.JSON)
      .build();
  }
}
