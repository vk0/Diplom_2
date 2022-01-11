package com;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class LoginIncorrectUserTest extends BaseClass{

    private final String payload;

    public LoginIncorrectUserTest(String payload) {
        this.payload = payload;
    }

    @Parameterized.Parameters
    public static Object[] getPayload() {

        return new Object[][]{
                {makeJSON("email", "test-data@yandex.ru", "password", getRandomString())},
                {makeJSON("email", getRandomString()+"@yandex.ru", "password", "password")},
        };
    }
    @Test
    public void createNewUserNotAllFieldTest(){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post("api/auth/login");

        response.then().assertThat()
                .statusCode(401);
    }
}
