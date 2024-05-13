package praktikum.api;

import io.restassured.response.Response;
import praktikum.testdata.User;
import praktikum.utils.Specifications;

import static io.restassured.RestAssured.given;

public class UserAPI {
    private final String login = "/api/auth/login";
    private final String user = "/api/auth/user";
    private final String register = "/api/auth/register";

    public Response createUser(User userBody){
        return given()
                .spec(Specifications.requestSpecification())
                .header("Content-type", "application/json")
                .body(userBody)
                .post(register);
    }

    public void deleteUser(String token){
         given()
                 .spec(Specifications.requestSpecification())
                 .header("Authorization", token)
                .delete(user);
    }

    public Response loginUser(User userBody, String token){
        return given()
                .spec(Specifications.requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(userBody)
                .post(login);
    }

    public Response updateUserData(User editData, String token){
        if(token != null) {
            return given()
                    .spec(Specifications.requestSpecification())
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
                    .body(editData)
                    .patch(user);
        } else {
            return given()
                    .spec(Specifications.requestSpecification())
                    .header("Content-type", "application/json")
                    .body(editData)
                    .patch(user);
        }
    }
}
