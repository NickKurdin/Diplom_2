package praktikum;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserAPI {
    private final String login = "/api/auth/login";
    private final String user = "/api/auth/user";
    private final String register = "/api/auth/register";

    public Response createUser(User userBody){
        return given()
                .header("Content-type", "application/json")
                .body(userBody)
                .post(register);
    }

    public void deleteUser(String token){
         given()
                 .header("Authorization", token)
                .delete(user);
    }

    public Response loginUser(User userBody, String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(userBody)
                .post(login);
    }

    public Response updateUserData(String editData, String token){
        if(token != null) {
            return given()
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
                    .body(editData)
                    .patch(user);
        } else {
            return given()
                    .header("Content-type", "application/json")
                    .body(editData)
                    .patch(user);
        }
    }
}
