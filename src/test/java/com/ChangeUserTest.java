package com;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ChangeUserTest extends BaseClass{
    //GET https://stellarburgers.nomoreparties.site/api/auth/user
    //PATCH https://stellarburgers.nomoreparties.site/api/auth/user

    @Test
    public void changeUserInfoWithoutTokenTest() {
        ArrayList<String> userArr = registerNewUserAndReturnLoginPassword("");
        String payload = makeJSON("email", userArr.get(0) + "@ya.ru", "password", userArr.get(1), "name", userArr.get(2));

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .patch("/api/auth/user");
        response.then().assertThat()
                .statusCode(401);
    }

    @Test
    public void changeUserInfoWithTokenTest() {
        ArrayList<String> userArr = registerNewUserAndReturnLoginPassword("");
        String payload = makeJSON("email", userArr.get(0) + "changed@ya.ru", "password", userArr.get(1), "name", userArr.get(2)+"changed");

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", userArr.get(3))
                .and()
                .body(payload)
                .when()
                .patch("/api/auth/user");
        response.then().assertThat()
                .statusCode(200);

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", userArr.get(3))
                .when()
                .get("/api/auth/user");
        String email = response.then().assertThat()
                .statusCode(200)
                .extract()
                .path("user.email");
        String name = response.then().assertThat()
                .statusCode(200)
                .extract()
                .path("user.name");

        Assert.assertEquals(email, userArr.get(0).toLowerCase() + "changed@ya.ru");
        Assert.assertEquals(name, userArr.get(2) + "changed");

    }
}
