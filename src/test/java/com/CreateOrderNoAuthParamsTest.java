package com;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderNoAuthParamsTest extends BaseClass{

    private final String payload;
    private int statusCode;

    public CreateOrderNoAuthParamsTest(String payload, int statusCode) {
        this.payload = payload;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[] getPayload() {
        return new Object[][]{
                {"{\"ingredients\": [] }", 400}, //empty
                {"{\"ingredients\": [\"1234567890\"] }", 500}, //non valid
                {"{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"] }", 200}, //
        };
    }

    @Test
    public void createOrdersTest(){
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "")
                .and()
                .body(payload)
                .when()
                .post("api/orders");

        response.then().assertThat()
                .statusCode(statusCode);
    }
}
