package org.example.restdemo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.util.Properties;

public class RestBaseTest {

  // Platform property passed in and verified in Gradle
  public static final String PLATFORM = System.getProperty("platform");

  // Values from config.properties
  private static String BASE_URI;

  // Load properties file only once for all test classes being run
  static {
    try {
      loadPropertiesFile();
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static RequestSpecification createRequestSpec() {
    return new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .setBaseUri(BASE_URI)
      // Here are some more options:
      //   .addHeader("headerName","headerValue")
      //   .addCookie(new Cookie.Builder("name", "value").setPath("...").setDomain("...").build())
      //   .addFilter(new ResponseLoggingFilter()) // Log request and response for better debugging
      //   .addFilter(new RequestLoggingFilter())  // You can also only log if a requests fails
      .build();
  }

  public static ResponseSpecification createResponseSpec() {
    return new ResponseSpecBuilder()
      .expectStatusCode(200)
      .expectContentType(ContentType.JSON)
      .build();
  }

  private static void loadPropertiesFile() throws IOException {
    Properties properties = new Properties();
    properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));

    // Platform verified in Gradle, so we kow it's either 'test' or 'staging'
    String platform = PLATFORM.equals("test") ? "test" : "staging";
    BASE_URI = properties.getProperty(platform + ".base.uri");
    System.out.println("******************* " + BASE_URI);
  }

}
