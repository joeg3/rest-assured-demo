package org.example.restdemo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Properties;

public class BaseTest {

  private static String baseUri;

  public static void setUpRequestSpec() throws Exception{

    Properties properties = new Properties();
    properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));

    String platform = System.getProperty("platform");

    if (platform.equals("test")) {
      baseUri = properties.getProperty("test.base.uri");
    } else {
      baseUri = properties.getProperty("staging.base.uri");
    }
    RestAssured.requestSpecification = new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .setBaseUri(baseUri)
      .build();
  }

}
