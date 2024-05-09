package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersUserTest extends APITesting {

    @Before
    public void setUp(){
        RestAssured.baseURI = url;
        OrdersAPI order = new OrdersAPI();
        firstIngredient = order.getIngredientsForOrder().then().extract().path("data[0]._id");
        secondIngredient = order.getIngredientsForOrder().then().extract().path("data[1]._id");
        orderBody = "{\n\"ingredients\": [\"" + firstIngredient +"\", \"" + secondIngredient +"\"]\n}";
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
        UserAPI user = new UserAPI();
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание заказа")
    public void createOrderForCheckStatusCodeAndResponseInCreateOrderWithAuthorization(){
        OrdersAPI order = new OrdersAPI();
        order.createOrder(orderBody, token);
    }
    @Step("Получение заказов пользователя, проверка статус кода и тела ответа")
    public void getOrdersUserForCheckStatusCodeAndResponse(){
        OrdersAPI order = new OrdersAPI();
        order.getOrdersUser(token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при получении заказов неавторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при получении заказов неавторизованного пользователя в ручке GET /api/orders")
    public void checkStatusCodeAndResponseInGetOrdersUnauthorizedUser(){
        OrdersAPI order = new OrdersAPI();
        token = "token";
        order.getOrdersUser(token).then().statusCode(401).and().body("success", equalTo(false));
    }


    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}
