package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest extends APITesting {

    @Before
    public void setUp(){
        RestAssured.baseURI = url;
        OrdersAPI order = new OrdersAPI();
        firstIngredient = order.getIngredientsForOrder().then().extract().path("data[0]._id");
        secondIngredient = order.getIngredientsForOrder().then().extract().path("data[1]._id");
        orderBody = "{\n\"ingredients\": [\"" + firstIngredient +"\", \"" + secondIngredient +"\"]\n}";
        orderBodyWithIncorrectIngredients = "{\n\"ingredients\": [\"" + firstIngredient +"\", \"" + secondIngredient + "0000oooo" +"\"]\n}";
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
        UserAPI user = new UserAPI();
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа, проверка статус кода и тела ответа")
    public void createOrderForCheckStatusCodeAndResponseInCreateOrderWithAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при создании заказа без авторизации")
    @Description("Проверка статус кода и тела ответа при создании заказа без авторизации в ручке POST /api/orders")
    public void checkStatusCodeAndResponseCreateOrderWithoutAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBody).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода при создании заказа без ингредиентов")
    @Description("Проверка статус кода при создании заказа без ингредиентов в ручке POST /api/orders")
    public void checkStatusCodeAndResponseCreateOrderWithoutIngredients(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBodyWithoutIngredients).then().statusCode(400).and().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода при создании заказа с некорректными ингридиентами")
    @Description("Проверка статус кода при создании заказа с некорректными ингридиентами в ручке POST /api/orders")
    public void checkStatusCodeCreateOrderWithIncorrectIngredients(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBodyWithIncorrectIngredients).then().statusCode(500);
    }

    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}
