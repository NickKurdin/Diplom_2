package praktikum.api;

import io.restassured.response.Response;
import praktikum.testdata.Order;
import praktikum.utils.Specifications;

import static io.restassured.RestAssured.given;

public class OrdersAPI {
    private final String orders = "/api/orders";
    private final String ingredients ="/api/ingredients";

    public Response createOrder(Order orderBody, String token){
            return given()
                    .spec(Specifications.requestSpecification())
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
                    .body(orderBody)
                    .post(orders);
    }

    public Response createOrderWithoutAuthorization(Order orderBody){
        return given()
                .spec(Specifications.requestSpecification())
                .header("Content-type", "application/json")
                .body(orderBody)
                .post(orders);
    }

    public Response getOrdersUser(String token){
        if(token != null){
            return given()
                    .spec(Specifications.requestSpecification())
                    .header("Authorization", token)
                    .get(orders);
        } else {
            return given()
                    .spec(Specifications.requestSpecification())
                    .get(orders);
        }
    }

    public Response getIngredientsForOrder(){
            return given()
                    .spec(Specifications.requestSpecification())
                    .get(ingredients);
    }

}
