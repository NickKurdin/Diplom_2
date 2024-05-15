package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.APITesting;
import praktikum.api.OrdersAPI;
import praktikum.api.UserAPI;
import praktikum.testdata.Order;
import praktikum.testdata.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersUserTest {
    private Order orderBodyForTest;
    private APITesting test;
    private UserAPI user;
    private OrdersAPI order;
    private User userBody;

    @Before
    public void setUp(){
        order = new OrdersAPI();
        orderBodyForTest = new Order();
        test = new APITesting();
        user = new UserAPI();
        userBody = new User(test.email, test.password, test.name);
        List<String> ingredients = new ArrayList<>();
        ingredients.add(test.firstIngredient);
        ingredients.add(test.secondIngredient);
        orderBodyForTest = orderBodyForTest.setIngredients(ingredients);
    }

    @Test
    @DisplayName("Проверка статус кода и тела ответа при получении заказов авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при получении заказов авторизованного пользователя в ручке GET /api/orders")
    public void checkStatusCodeAndResponseInGetOrdersUser(){
        createUserForCheckStatusCodeAndResponseInGetOrdersUser();
        createOrderForCheckStatusCodeAndResponseInCreateOrderWithAuthorization();
        getOrdersUserForCheckStatusCodeAndResponse();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInGetOrdersUser(){
        test.token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа")
    public void createOrderForCheckStatusCodeAndResponseInCreateOrderWithAuthorization(){
        order.createOrder(orderBodyForTest, test.token);
    }
    @Step("Получение заказов пользователя, проверка статус кода и тела ответа")
    public void getOrdersUserForCheckStatusCodeAndResponse(){
        order.getOrdersUser(test.token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при получении заказов неавторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при получении заказов неавторизованного пользователя в ручке GET /api/orders")
    public void checkStatusCodeAndResponseInGetOrdersUnauthorizedUser(){
        test.token = "token";
        order.getOrdersUser(test.token).then().statusCode(401).and().body("success", equalTo(false));
    }


    @After
    public void deleteUser(){
        if(test.token != null){
            user.deleteUser(test.token);
        }
    }
}
