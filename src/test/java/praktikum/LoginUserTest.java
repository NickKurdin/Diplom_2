package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    public String userBodyForCreate = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\",\n\"name\": \"nikkex12345678\"\n}";
    public String token;
    public String userBodyWithIncorrectPassword = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nickex123456789\"\n}";
    public String userBodyWithCorrectPassword = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\"\n}";

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }


    @Test
    @DisplayName("Проверка статус кода при авторизации пользователя с корректными данными")
    @Description("Проверка статус кода при авторизации пользователя с корректными данными в ручке POST /api/auth/login")
    public void checkStatusCodeInLoginUserWithCorrectPass(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCode(user);
        loginUserAndCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCode(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя и проверка статус кода")
    public void loginUserAndCheckStatusCode(UserAPI user){
        token = user.loginUser(userBodyWithCorrectPassword, token).then().statusCode(200).extract().path("accessToken");
    }

    @Test
    @DisplayName("Проверка тела ответа при авторизации пользователя с корректными данными")
    @Description("Проверка тела ответа при авторизации пользователя с корректными данными в ручке POST /api/auth/login")
    public void checkResponseInLoginUserWithCorrectPass(){
        UserAPI user = new UserAPI();
        createUserForCheckResponse(user);
        loginUserAndCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckResponse(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя и проверка статус кода")
    public void loginUserAndCheckResponse(UserAPI user){
        token = user.loginUser(userBodyWithCorrectPassword, token).then().body("success", equalTo(true)).extract().path("accessToken");
    }


    @Test
    @DisplayName("Проверка статус кода при авторизации пользователя с некорректными данными")
    @Description("Проверка статус кода при авторизации пользователя с некорректными данными в ручке POST /api/auth/login")
    public void checkStatusCodeInLoginUserWithIncorrectPass(){
        UserAPI user = new UserAPI();
        createUserWithIncorrectPassForCheckStatusCode(user);
        loginUserWithIncorrectPassAndCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserWithIncorrectPassForCheckStatusCode(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя и проверка статус кода")
    public void loginUserWithIncorrectPassAndCheckStatusCode(UserAPI user){
        user.loginUser(userBodyWithIncorrectPassword, token).then().statusCode(401);
    }

    @Test
    @DisplayName("Проверка тела ответа при авторизации пользователя с некорректными данными")
    @Description("Проверка тела ответа при авторизации пользователя с некорректными данными в ручке POST /api/auth/login")
    public void checkResponseInLoginUserWithIncorrectPass(){
        UserAPI user = new UserAPI();
        createUserWithIncorrectPassForCheckResponse(user);
        loginUserWithIncorrectPassAndCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserWithIncorrectPassForCheckResponse(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя и проверка тела ответа")
    public void loginUserWithIncorrectPassAndCheckResponse(UserAPI user){
        user.loginUser(userBodyWithIncorrectPassword, token).then().body("success", equalTo(false)).extract().path("accessToken");
    }


    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}