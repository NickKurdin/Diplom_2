package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    public String userBody = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\",\n\"name\": \"nikkex12345678\"\n}";
    public String token;
    public String orderBody = "{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6f\"]\n}";
    public String orderBodyWithoutIngredients = "{\n\"ingredients\": []\n}";
    public String orderBodyWithIncorrectIngredients = "{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6dnickex\", \"61c0c5a71d1f82001bdaaa6fnickex\"]\n}";


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Проверка статус кода при создании заказа с авторизацией")
    @Description("Проверка статус кода при создании заказа с авторизацией в ручке POST /api/orders")
    public void checkStatusCodeCreateOrderWithAuthorization(){
        createUserForCheckStatusCodeInCreateOrderWithAuthorization();
        createOrderForCheckStatusCodeInCreateOrderWithAuthorization();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInCreateOrderWithAuthorization(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа и проверка статус кода")
    public void createOrderForCheckStatusCodeInCreateOrderWithAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token).then().statusCode(200);
    }

    @Test
    @DisplayName("Проверка тела ответа при создании заказа с авторизацией")
    @Description("Проверка тела ответа при создании заказа с авторизацией в ручке POST /api/orders")
    public void checkResponseCreateOrderWithAuthorization(){
        createUserForResponseCodeInCreateOrderWithAuthorization();
        createOrderForCheckResponseInCreateOrderWithAuthorization();
    }
    @Step("Создание пользователя")
    public void createUserForResponseCodeInCreateOrderWithAuthorization(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа и проверка тела ответа")
    public void createOrderForCheckResponseInCreateOrderWithAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token).then().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода при создании заказа без авторизации")
    @Description("Проверка статус кода при создании заказа без авторизации в ручке POST /api/orders")
    public void checkStatusCodeCreateOrderWithoutAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBody).then().statusCode(200);
    }

    @Test
    @DisplayName("Проверка тела ответа при создании заказа без авторизации")
    @Description("Проверка тела ответа при создании заказа без авторизации в ручке POST /api/orders")
    public void checkResponseCreateOrderWithoutAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBody).then().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода при создании заказа без ингредиентов")
    @Description("Проверка статус кода при создании заказа без ингредиентов в ручке POST /api/orders")
    public void checkStatusCodeCreateOrderWithoutIngredients(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBodyWithoutIngredients).then().statusCode(400);
    }

    @Test
    @DisplayName("Проверка тела ответа при создании заказа без ингредиентов")
    @Description("Проверка тела ответа при создании заказа без ингредиентов в ручке POST /api/orders")
    public void checkResponseCreateOrderWithoutIngredients(){
        OrdersAPI order = new OrdersAPI();
        order.createOrderWithoutAuthorization(orderBodyWithoutIngredients).then().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода при создании заказа без ингредиентов")
    @Description("Проверка статус кода при создании заказа без ингредиентов в ручке POST /api/orders")
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
