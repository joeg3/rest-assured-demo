package org.example;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.restdemo.dto.PostDTO;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PojoTest {

    //@Test
    public void validateInternationalSpaceStationCurrentLocationContents() {

        RestAssured.baseURI = "http://api.open-notify.org";

        Response response =
                given().
                        contentType("application/json").
                 when().
                        get("/iss-now/").
                 then().
                        statusCode(200).
                        body(containsString("iss_position")).
                        body(containsString("message")).
                        body(containsString("timestamp")).
                        body(("message"), equalTo("success")).
                        extract().response();

        JsonPath js = new JsonPath(response.asString());
        //String issueId = js.get("id");
        System.out.println("JsonPath: " + js);

    }

    //@Test
    public void getExample() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response =
                given().
                        contentType("application/json").
                        when().
                        get("/posts/1").
                        then().
                        statusCode(200).
                        body(containsString("title")).
                        body(containsString("body")).
                        body(containsString("userId")).
                        body(("id"), equalTo(1)).
                        extract().response();

        JsonPath jsonPathResponse = new JsonPath(response.asString());
        System.out.println("### GET ###");

        System.out.println("### RESPONSE ###");
        System.out.println(response.getBody().asString());

        System.out.println("### JSONPATH INDIVIDUAL VALUES ###");
        System.out.println("id: " + jsonPathResponse.get("id"));
        System.out.println("title: " + jsonPathResponse.get("title"));
        System.out.println("body: " + jsonPathResponse.get("body"));
        System.out.println("userId: " + jsonPathResponse.get("userId"));
    }

    // Udemy course example
    @Test
    public void getExampleWithPojos() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        PostDTO postDTO =
                given().
                        contentType("application/json").expect().defaultParser(Parser.JSON).
                        when().
                        get("/posts/1").
                        as(PostDTO.class);
        
        System.out.println("### GET WITH POJOS ###");

        System.out.println("### RESPONSE ###");
        System.out.println(postDTO.toString());

        System.out.println("### POJO INDIVIDUAL VALUES ###");
        System.out.println("id: " + postDTO.getId());
        System.out.println("title: " + postDTO.getTitle());
        System.out.println("body: " + postDTO.getBody());
        System.out.println("userId: " + postDTO.getUserId());
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
