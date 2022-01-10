package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateNewUserNotAllFieldTest extends BaseClass{
    private final String payload;
    public CreateNewUserNotAllFieldTest(String payload) {
        this.payload = payload;
    }

    @Parameterized.Parameters
        public static Object[] getPayload() {
            return new Object[][]{
                    {makeJSON("email", "test-data_3@yandex.ru", "password", "password")},
                    {makeJSON("email", "test-data_3@yandex.ru", "name", "Username")},
                    {makeJSON("password", "password", "name", "Username")},
            };
        }

        @Test
        public void createNewUserNotAllFieldTest(){
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(payload)
                    .when()
                    .post("/api/auth/register")
                    .then().assertThat()
                    .statusCode(403);
        }
}
