package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserTest extends FunctionalTests {

    private static final String GET_USER_API = "/blog/user/find";

    @Test
    void shouldFindOneUserWithNameAdi() {
        int expectedNumberOfUsers = 1;
        Response response = given().param("searchString", "Adi")
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(GET_USER_API);
        List<String> jsonResponse = response.jsonPath().getList("$");
        Assertions.assertEquals(expectedNumberOfUsers, jsonResponse.size());
    }

    @Test
    void shouldFindTwoUserWithNameBrian() {
        int expectedNumberOfUsers = 3;
        Response response = given().param("searchString", "Brian")
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(GET_USER_API);
        List<String> jsonResponse = response.jsonPath().getList("$");
        Assertions.assertEquals(expectedNumberOfUsers, jsonResponse.size());
    }

    @Test
    void shouldNotFindUserWithStatusREMOVEDByEmail(){
        int expectedNumberOfUsers = 0;
        Response response = given().param("searchString", "delted@domain.com")
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(GET_USER_API);
        List<String> jsonResponse = response.jsonPath().getList("$");
        Assertions.assertEquals(expectedNumberOfUsers, jsonResponse.size());
    }
}
