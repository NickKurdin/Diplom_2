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

public class UpdateUserDataTest {
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
    @DisplayName("Проверка статус кода и тела ответа при обновлении имени авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении имени авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUserName(){
        createUserForCheckStatusCodeAndResponseInUpdateUserName(user);
        updateUserNameForCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInUpdateUserName(UserAPI user){
        test.token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Обновление имени пользователя")
    public void updateUserNameForCheckStatusCodeAndResponse(UserAPI user){
        user.updateUserData(userBody.setNameForBody(test.updateUserName), test.token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении имени неавторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении имени неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUnauthorizedUserName(){
        test.token = "token";
        user.updateUserData(userBody.setNameForBody(test.updateUserName), test.token).then().statusCode(401).and().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении почты авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении почты авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUserEmail(){
        createUserForCheckStatusCodeAndResponseInUpdateUserEmail(user);
        updateUserEmailForCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInUpdateUserEmail(UserAPI user){
        test.token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserEmailForCheckStatusCodeAndResponse(UserAPI user){
        user.updateUserData(userBody.setEmailForBody(test.updateUserEmail), test.token).then().statusCode(200).and().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении почты неавторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении почты неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusAndResponseCodeAndUpdateUnauthorizedUserEmail(){
        test.token = "token";
        user.updateUserData(userBody.setNameForBody(test.updateUserEmail), test.token).then().statusCode(401).and().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода и тела ответа при обновлении почты занятым значением авторизованного пользователя")
    @Description("Проверка статус кода и тела ответа при обновлении почты занятым значением авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndResponseUpdateUserExistEmail(){
        createUserForCheckStatusCodeAndResponseInUpdateUserExistEmail(user);
        updateUserExistEmailForCheckStatusCodeAndResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeAndResponseInUpdateUserExistEmail(UserAPI user){
        test.token = user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserExistEmailForCheckStatusCodeAndResponse(UserAPI user){
        user.updateUserData(userBody.setEmailForBody(test.updateExistUserEmail), test.token).then().statusCode(403).and().body("message", equalTo("User with such email already exists"));
    }


    @After
    public void deleteUser(){
        if(test.token != null){
            user.deleteUser(test.token);
        }
    }
}
