package praktikum;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersAPI {
    public Response createOrder(String orderBody, String token){
            return given()
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
                    .body(orderBody)
                    .post("/api/orders");
    }

    public Response createOrderWithoutAuthorization(String orderBody){
        return given()
                .header("Content-type", "application/json")
                .body(orderBody)
                .post("/api/orders");
    }

    public Response getOrdersUser(String token, boolean authorized){
        if(authorized){
            return given()
                    .header("Authorization", token)
                    .get("/api/orders");
        } else {
            return given()
                    .get("/api/orders");
        }
    }

}
