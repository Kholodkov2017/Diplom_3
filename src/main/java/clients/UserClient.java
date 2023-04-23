package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static helpers.Constants.*;
import static io.restassured.RestAssured.given;

public class UserClient {
    @Step("Attempt to delete user")
    public static ValidatableResponse getUserDeleteResponse(String token) {
        return given()
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE)
                .auth().oauth2(token)
                .delete(USER_PTH)
                .then();
    }

    @Step("Attempt to make user creation request")
    public static ValidatableResponse getUserCreationResponse(Object body) {
        return given()
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE)
                .and()
                .body(body)
                .post(REGISTER_PTH)
                .then();
    }
}
