package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersUserTest {
    public String userBody = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\",\n\"name\": \"nikkex12345678\"\n}";
    public String token;
    public String orderBody = "{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6f\"]\n}";


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Проверка статус кода при получении заказов авторизованного пользователя")
    @Description("Проверка статус кода при получении заказов авторизованного пользователя в ручке GET /api/orders")
    public void checkStatusCodeInGetOrdersUser(){
        createUserForCheckStatusCodeInGetOrdersUser();
        createOrderForCheckStatusCodeInCreateOrderWithAuthorization();
        getOrdersUserForCheckStatusCode();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInGetOrdersUser(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа")
    public void createOrderForCheckStatusCodeInCreateOrderWithAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token);
    }
    @Step("Получение заказов пользователя и проверка статус кода")
    public void getOrdersUserForCheckStatusCode(){
        OrdersAPI order = new OrdersAPI();
        order.getOrdersUser(token, true).then().statusCode(200);
    }

    @Test
    @DisplayName("Проверка тела ответа при получении заказов авторизованного пользователя")
    @Description("Проверка тела ответа при получении заказов авторизованного пользователя в ручке GET /api/orders")
    public void checkResponseInGetOrdersUser(){
        createUserForCheckResponseInGetOrdersUser();
        createOrderForCheckResponseInCreateOrderWithAuthorization();
        getOrdersUserForCheckResponse();
    }
    @Step("Создание пользователя")
    public void createUserForCheckResponseInGetOrdersUser(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа")
    public void createOrderForCheckResponseInCreateOrderWithAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token);
    }
    @Step("Получение заказов пользователя и проверка тела ответа")
    public void getOrdersUserForCheckResponse(){
        OrdersAPI order = new OrdersAPI();
        order.getOrdersUser(token, true).then().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода при получении заказов неавторизованного пользователя")
    @Description("Проверка статус кода при получении заказов неавторизованного пользователя в ручке GET /api/orders")
    public void checkStatusCodeInGetOrdersUnauthorizedUser(){
        createUserForCheckStatusCodeInGetOrdersUnauthorizedUser();
        createOrderForCheckStatusCodeInGetOrdersUnauthorizedUser();
        getOrdersUnauthorizedUserForCheckStatusCode();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInGetOrdersUnauthorizedUser(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа")
    public void createOrderForCheckStatusCodeInGetOrdersUnauthorizedUser(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token);
    }
    @Step("Получение заказов пользователя и проверка статус кода")
    public void getOrdersUnauthorizedUserForCheckStatusCode(){
        OrdersAPI order = new OrdersAPI();
        order.getOrdersUser(token, false).then().statusCode(401);
    }

    @Test
    @DisplayName("Проверка тела ответа при получении заказов неавторизованного пользователя")
    @Description("Проверка тела ответа при получении заказов неавторизованного пользователя в ручке GET /api/orders")
    public void checkResponseInGetOrdersUnauthorizedUser(){
        createUserForCheckResponseInGetOrdersUnauthorizedUser();
        createOrderForCheckResponseInGetOrdersUnauthorizedUser();
        getOrdersUnauthorizedUserForCheckResponse();
    }
    @Step("Создание пользователя")
    public void createUserForCheckResponseInGetOrdersUnauthorizedUser(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа")
    public void createOrderForCheckResponseInGetOrdersUnauthorizedUser(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token);
    }
    @Step("Получение заказов пользователя и проверка тела ответа")
    public void getOrdersUnauthorizedUserForCheckResponse(){
        OrdersAPI order = new OrdersAPI();
        order.getOrdersUser(token, false).then().body("success", equalTo(false));
    }

    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}
