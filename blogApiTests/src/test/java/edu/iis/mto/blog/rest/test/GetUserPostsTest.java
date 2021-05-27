package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.util.List;

public class GetUserPostsTest extends FunctionalTests {

    private static final String GET_USER_POSTS_API = "/blog/user/{id}/post";

    @Test
    void canNotFindUserPostsWithStatusREMOVED() {
        JSONObject jsonObj = new JSONObject();
        Long deletedUserId = 11l;
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(GET_USER_POSTS_API, deletedUserId);
    }

    @Test
    void getPostLikesShouldReturnThreeLikes() {
        int numberOfExpectedLikes = 3;
        Long userId = 13l;
        Response response = given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get(GET_USER_POSTS_API, userId);

        String returnLikesCount = response.jsonPath().getString("likesCount[0]");
        assertEquals(numberOfExpectedLikes, Integer.valueOf(returnLikesCount));
    }

    @Test
    void getPostShouldReturnTwoPosts() {
        int numberOfExpectedPosts = 2;
        Long userId = 13l;
        Response response = given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get(GET_USER_POSTS_API, userId);

        List<String> jsonResponse = response.jsonPath().getList("$");
        Assertions.assertEquals(numberOfExpectedPosts, jsonResponse.size());
    }
}
