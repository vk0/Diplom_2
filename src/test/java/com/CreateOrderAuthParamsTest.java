package com;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;


public class CreateOrderAuthParamsTest extends BaseClass{

   String token;

   @Before
   public void setup(){
       token = registerNewUserAndReturnLoginPassword("").get(3);
   }


    @Test
    public void createAuthOrdersEmptyIngredientsTest(){
       String payload = "{\"ingredients\": [] }";
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(payload)
                .when()
                .post("api/orders");

        response.then().assertThat()
                .statusCode(400);
    }

    @Test
    public void createAuthOrdersNonValidIngredientsTest(){
        String payload = "{\"ingredients\": [\"1234567890\"] }";
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(payload)
                .when()
                .post("api/orders");

        response.then().assertThat()
                .statusCode(500);
    }

    @Test
    public void createAuthOrdersValidIngredientsTest(){
        String payload = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"] }";
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(payload)
                .when()
                .post("api/orders");

        response.then().assertThat()
                .statusCode(200);
    }

}
