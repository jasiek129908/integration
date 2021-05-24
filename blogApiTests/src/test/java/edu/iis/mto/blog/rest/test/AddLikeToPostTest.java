package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class AddLikeToPostTest {

    private static final String ADD_LIKE_TO_POST_API = "/blog/user/{userId}/like/{postId}";

    @Test
    void userWithStatusCONFFIRMEDCanLikePostStatusOk() {
        JSONObject jsonObj = new JSONObject();
        Long postId = 1l;
        Long userIdWithStatusCONFIRMED = 1l;
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(ADD_LIKE_TO_POST_API, userIdWithStatusCONFIRMED, postId);
    }

    @Test
    void userWithDifferentStatusThanCONFFIRMEDCanLikePost() {
        JSONObject jsonObj = new JSONObject();
        Long postId = 1l;
        Long userIdWithStatusDifferentThanCONFIRMED = 2l;
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(ADD_LIKE_TO_POST_API, userIdWithStatusDifferentThanCONFIRMED, postId);
    }

    @Test
    void postOwnerCanNotLikeHisOwnPost() {
        JSONObject jsonObj = new JSONObject();
        Long postId = 1l;
        Long authorId = 9l;
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(ADD_LIKE_TO_POST_API, authorId, postId);
    }

    @Test
    void canNotLikeSamePostTwice() {
        JSONObject jsonObj = new JSONObject();
        Long postId = 1l;
        Long authorIdThatLikedPostAlready = 10l;
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body("false")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(ADD_LIKE_TO_POST_API, authorIdThatLikedPostAlready, postId);
    }
}
