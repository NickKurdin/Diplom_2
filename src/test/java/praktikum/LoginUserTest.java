package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest extends APITesting{

    @Before
    public void setUp(){
        RestAssured.baseURI = url;
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при авторизации пользователя с корректными данными")
    @Description("Проверка статус кода и тела ответа при авторизации пользователя с корректными данными в ручке POST /api/auth/login")
    public void checkStatusCodeAndResponseInLoginUserWithCorrectPass(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeAndResponse(user);
        loginUserAndCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponse(UserAPI user){
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя, проверка статус кода и тела ответа")
    public void loginUserAndCheckStatusCodeAndResponse(UserAPI user){
        token = user.loginUser(userBody, token).then().statusCode(200).and().body("success", equalTo(true)).extract().path("accessToken");
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при авторизации пользователя с некорректными данными")
    @Description("Проверка статус кода и тела ответа при авторизации пользователя с некорректными данными в ручке POST /api/auth/login")
    public void checkStatusCodeInLoginUserWithIncorrectPassAndResponse(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeAndResponseAfterLoginWithIncorrectPassword(user);
        loginUserWithIncorrectPassAndCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseAfterLoginWithIncorrectPassword(UserAPI user){
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя, проверка статус кода и тела ответа")
    public void loginUserWithIncorrectPassAndCheckStatusCodeAndResponse(UserAPI user){
        userBody = new User(email, incorrectPassword);
        user.loginUser(userBody, token).then().statusCode(401).and().assertThat().body("success", equalTo(false));
    }


    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}