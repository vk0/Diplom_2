package com;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetOrdersTest extends BaseClass {
    String token;

    @Before
    public void setup(){
        token = registerNewUserAndReturnLoginPassword("").get(3);
    }

    @Test
    public void getOrdersNoAuthTest(){
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("api/orders");

        response.then().assertThat()
                .statusCode(401);
    }

    @Test
    public void getOrdersAuthTest(){
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get("api/orders");

        response.then().assertThat()
                .statusCode(200);
    }
}
