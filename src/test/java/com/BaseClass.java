package com;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class BaseClass {

    public static String makeJSON(String ...arg) {
        JSONObject jo = new JSONObject();
        try {
            for (int i = 0; i < arg.length; i++) {
                jo.put(arg[i], arg[i + 1]);
                i++;
            }
            JSONArray messages = new JSONArray();
        /*
        String[] m = arr.split(",");
        for (int i=0;i<m.length;i++){
            messages.put(m[i]);
        }
        jo.put("color", messages);
        */

            System.out.println(jo.toString());
        }
        catch (  Exception e) {}
        return jo.toString();
    }



    @Before
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    public void tearDown(){

    }

    public static String getRandomString(){
        return RandomStringUtils.randomAlphabetic(10);
    }

    public  ArrayList<String> getIngredients(){
        Response response = given()
                .when()
                .get("api/ingredients");

        ArrayList<String> data = response.then().assertThat()
                .body("data", notNullValue())
                .and()
                .statusCode(200)
                .and()
                .extract()
                .path("data._id");
        return data;
    }

    public static ArrayList<String> registerNewUserAndReturnLoginPassword(String p){
        String login = getRandomString();
        String password = getRandomString();
        String name = getRandomString();

        ArrayList<String> loginPass = new ArrayList<>();

        String payload = "";
        if(p.equals("")) {
            payload = makeJSON("email", login + "@ya.ru", "password", password, "name", name);
        }

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post("api/auth/register");

        String token =response.then().assertThat()
                .body("accessToken", notNullValue())
                .and()
                .statusCode(200)
                .extract()
                .path("accessToken");
        System.out.println(token);

        if (response.statusCode() == 200) {
            loginPass.add(login);
            loginPass.add(password);
            loginPass.add(name);
            loginPass.add(token);
        }
        return loginPass;
    }
}
