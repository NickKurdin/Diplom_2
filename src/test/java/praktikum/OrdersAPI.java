package praktikum;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersAPI {
    private final String orders = "/api/orders";
    private final String ingredients ="/api/ingredients";

    public Response createOrder(String orderBody, String token){
            return given()
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
                    .body(orderBody)
                    .post(orders);
    }

    public Response createOrderWithoutAuthorization(String orderBody){
        return given()
                .header("Content-type", "application/json")
                .body(orderBody)
                .post(orders);
    }

    public Response getOrdersUser(String token){
        if(token != null){
            return given()
                    .header("Authorization", token)
                    .get(orders);
        } else {
            return given()
                    .get(orders);
        }
    }

    public Response getIngredientsForOrder(){
            return given()
                    .get(ingredients);
    }

}
