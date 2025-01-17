package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;


class CreatePostTest extends FunctionalTests {

    private static final String CREATE_POST_API = "/blog/user/{userid}/post";

    @Test
    void createPostByAnyUserStatusThanCONFIRMEDShouldWork() {
        JSONObject jsonObj = new JSONObject();
        Long userIdWithStatusDifferentThanCONFIRMED = 2l;
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(CREATE_POST_API,userIdWithStatusDifferentThanCONFIRMED);
    }
}