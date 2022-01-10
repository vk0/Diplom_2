package com;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseClass {

    @Test
    public void loginCorrectUserTest(){
        ArrayList<String> userArr = registerNewUserAndReturnLoginPassword("");
        String payload = makeJSON("email", userArr.get(0)+"@ya.ru", "password", userArr.get(1));

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post("api/auth/login");

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .and()
                .body("refreshToken", notNullValue());
    }
}
