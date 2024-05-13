package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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

public class CreateOrderTest{
    private Order orderBodyForTest;
    private APITesting test;
    private UserAPI user;
    private OrdersAPI order;
    private User userBody;


    @Before
    public void setUp(){
        order = new OrdersAPI();
        orderBodyForTest = new Order();
        user = new UserAPI();
        userBody = new User();
        test = new APITesting();
        userBody = new User(test.email, test.password, test.name);
        List<String> ingredients = new ArrayList<>();
        ingredients.add(test.firstIngredient);
        ingredients.add(test.secondIngredient);
        orderBodyForTest = orderBodyForTest.setIngredients(ingredients);
    }

    @Test
    @DisplayName("Проверка статус кода и тела ответа при создании заказа с авторизацией")
    @Description("Проверка статус кода и тела ответа при создании заказа с авторизацией в ручке POST /api/orders")
    public void checkStatusCodeAndResponseCreateOrderWithAuthorization(){
        createUserForCheckStatusCodeAndResponseInCreateOrderWithAuthorization();
        createOrderForCheckStatusCodeAndResponseInCreateOrderWithAuthorization();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInCreateOrderWithAuthorization(){
        test.token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа, проверка статус кода и тела ответа")
    public void createOrderForCheckStatusCodeAndResponseInCreateOrderWithAuthorization(){
        order.createOrder(orderBodyForTest, test.token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при создании заказа без авторизации")
    @Description("Проверка статус кода и тела ответа при создании заказа без авторизации в ручке POST /api/orders")
    public void checkStatusCodeAndResponseCreateOrderWithoutAuthorization(){
        order.createOrderWithoutAuthorization(orderBodyForTest).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при создании заказа без ингредиентов")
    @Description("Проверка статус кода и тела ответа при создании заказа без ингредиентов в ручке POST /api/orders")
    public void checkStatusCodeAndResponseCreateOrderWithoutIngredients(){
        order.createOrderWithoutAuthorization(orderBodyForTest.setIngredients(test.orderBodyWithoutIngredients)).then().statusCode(400).and().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода при создании заказа с некорректными ингридиентами")
    @Description("Проверка статус кода при создании заказа с некорректными ингридиентами в ручке POST /api/orders")
    public void checkStatusCodeCreateOrderWithIncorrectIngredients(){
        order.createOrderWithoutAuthorization(orderBodyForTest.setIngredients(test.incorrectIngredients)).then().statusCode(500);
    }

    @After
    public void deleteUser(){
        if(test.token != null){
            user.deleteUser(test.token);
        }
    }
}
