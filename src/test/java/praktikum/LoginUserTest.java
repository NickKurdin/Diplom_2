package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.APITesting;
import praktikum.api.UserAPI;
import praktikum.testdata.User;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest extends APITesting {
    private APITesting test;
    private UserAPI user;
    private User userBody;


    @Before
    public void setUp(){
        test = new APITesting();
        user = new UserAPI();
        userBody = new User(test.email, test.password, test.name);
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при авторизации пользователя с корректными данными")
    @Description("Проверка статус кода и тела ответа при авторизации пользователя с корректными данными в ручке POST /api/auth/login")
    public void checkStatusCodeAndResponseInLoginUserWithCorrectPass(){
        createUserForCheckStatusCodeAndResponse();
        loginUserAndCheckStatusCodeAndResponse();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponse(){
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Авторизация пользователя, проверка статус кода и тела ответа")
    public void loginUserAndCheckStatusCodeAndResponse(){
        token = user.loginUser(userBody, token).then().statusCode(200).and().body("success", equalTo(true)).extract().path("accessToken");
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при авторизации пользователя с некорректными данными")
    @Description("Проверка статус кода и тела ответа при авторизации пользователя с некорректными данными в ручке POST /api/auth/login")
    public void checkStatusCodeInLoginUserWithIncorrectPassAndResponse(){
        createUserForCheckStatusCodeAndResponseAfterLoginWithIncorrectPassword();
        loginUserWithIncorrectPassAndCheckStatusCodeAndResponse();
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseAfterLoginWithIncorrectPassword(){
        token = user.createUser(userBody).then().extract().path("accessToken");
        userBody = new User(test.email, test.incorrectPassword);
    }
    @Step("Авторизация пользователя, проверка статус кода и тела ответа")
    public void loginUserWithIncorrectPassAndCheckStatusCodeAndResponse(){
        user.loginUser(userBody, token).then().statusCode(401).and().assertThat().body("success", equalTo(false));
    }


    @After
    public void deleteUser(){
        if(token != null){
            user.deleteUser(token);
        }
    }
}