package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserDataTest extends APITesting {

    @Before
    public void setUp(){
        RestAssured.baseURI = url;
    }

    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении имени авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении имени авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUserName(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeAndResponseInUpdateUserName(user);
        updateUserNameForCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInUpdateUserName(UserAPI user){
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Обновление имени пользователя")
    public void updateUserNameForCheckStatusCodeAndResponse(UserAPI user){
        user.updateUserData(updateUserName, token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении имени неавторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении имени неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUnauthorizedUserName(){
        UserAPI user = new UserAPI();
        token = "token";
        user.updateUserData(updateUserName, token).then().statusCode(401).and().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении почты авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении почты авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUserEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeAndResponseInUpdateUserEmail(user);
        updateUserEmailForCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInUpdateUserEmail(UserAPI user){
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserEmailForCheckStatusCodeAndResponse(UserAPI user){
        user.updateUserData(updateUserEmail, token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении почты неавторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении почты неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusAndResponseCodeAndUpdateUnauthorizedUserEmail(){
        UserAPI user = new UserAPI();
        token = "token";
        user.updateUserData(updateUserEmail, token).then().statusCode(401).and().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении почты занятым значением авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении почты занятым значением авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUserExistEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeAndResponseInUpdateUserExistEmail(user);
        updateUserExistEmailForCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInUpdateUserExistEmail(UserAPI user){
        userBody = new User(email, password, name);
        token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserExistEmailForCheckStatusCodeAndResponse(UserAPI user){
        user.updateUserData(updateExistUserEmail, token).then().statusCode(403).and().body("message", equalTo("User with such email already exists"));
    }


    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}
