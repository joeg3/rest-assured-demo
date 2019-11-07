package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class BasicTest {

    @Test
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



}
