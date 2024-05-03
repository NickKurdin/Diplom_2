package praktikum;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserAPI {
    public Response createUser(String userBody){
        return given()
                .header("Content-type", "application/json")
                .body(userBody)
                .post("/api/auth/register");
    }

    public void deleteUser(String token){
         given()
                 .header("Authorization", token)
                .delete("/api/auth/user");
    }

    public Response loginUser(String userBody, String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(userBody)
                .post("/api/auth/login");
    }
}
