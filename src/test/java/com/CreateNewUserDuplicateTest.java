package com;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CreateNewUserDuplicateTest extends BaseClass{
    //создать пользователя, который уже зарегистрирован;
    @Test
    public void createNewUserDuplicateTest() {
        ArrayList<String> userArr = registerNewUserAndReturnLoginPassword("");
        //System.out.println(userArr);
        String payload = makeJSON("email", userArr.get(0)+"@ya.ru", "password", userArr.get(1), "name", userArr.get(2));

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post("api/auth/register");
        response.then().assertThat()
                .statusCode(403);


    }
}
