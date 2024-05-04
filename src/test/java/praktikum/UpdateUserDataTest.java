package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserDataTest {
    public String userBodyForCreate = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\",\n\"name\": \"nikkex12345678\"\n}";
    public String token;
    public String updateUserName = "{\n\"name\": \"nickex\"\n}";
    public String updateExistUserEmail = "{\n\"email\": \"nickex@yandex.ru\"\n}";
    public String updateUserEmail = "{\n\"email\": \"nikkex12345678000@yandex.ru\"\n}";

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Проверка статус кода при обновлении имени авторизованного пользователя")
    @Description("Проверка статус кода при обновлении имени авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndUpdateUserName(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeInUpdateUserName(user);
        updateUserNameForCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInUpdateUserName(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление имени пользователя")
    public void updateUserNameForCheckStatusCode(UserAPI user){
        user.updateUserData(updateUserName, token, true).then().statusCode(200);
    }

    @Test
    @DisplayName("Проверка тела ответа при обновлении имени авторизованного пользователя")
    @Description("Проверка тела ответа при обновлении имени авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkResponseAndUpdateUserName(){
        UserAPI user = new UserAPI();
        createUserForResponseCodeInUpdateUserName(user);
        updateUserNameForCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForResponseCodeInUpdateUserName(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление имени пользователя")
    public void updateUserNameForCheckResponse(UserAPI user){
        user.updateUserData(updateUserName, token, true).then().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода при обновлении имени неавторизованного пользователя")
    @Description("Проверка статус кода при обновлении имени неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndUpdateUnauthorizedUserName(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeInUnauthorizedUpdateUserName(user);
        updateUnauthorizedUserNameForCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInUnauthorizedUpdateUserName(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление имени пользователя")
    public void updateUnauthorizedUserNameForCheckStatusCode(UserAPI user){
        user.updateUserData(updateUserName, token, false).then().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Проверка тела ответа при обновлении имени неавторизованного пользователя")
    @Description("Проверка тела ответа при обновлении имени неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkResponseAndUpdateUnauthorizedUserName(){
        UserAPI user = new UserAPI();
        createUserForCheckResponseInUnauthorizedUpdateUserName(user);
        updateUnauthorizedUserNameForCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckResponseInUnauthorizedUpdateUserName(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление имени пользователя")
    public void updateUnauthorizedUserNameForCheckResponse(UserAPI user){
        user.updateUserData(updateUserName, token, false).then().statusCode(401);
    }


    @Test
    @DisplayName("Проверка статус кода при обновлении почты авторизованного пользователя")
    @Description("Проверка статус кода при обновлении почты авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndUpdateUserEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeInUpdateUserEmail(user);
        updateUserEmailForCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInUpdateUserEmail(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserEmailForCheckStatusCode(UserAPI user){
        user.updateUserData(updateUserEmail, token, true).then().statusCode(200);
    }

    @Test
    @DisplayName("Проверка тела ответа при обновлении почты авторизованного пользователя")
    @Description("Проверка тела ответа при обновлении почты авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkResponseAndUpdateUserEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckResponseInUpdateUserEmail(user);
        updateUserEmailForCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckResponseInUpdateUserEmail(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserEmailForCheckResponse(UserAPI user){
        user.updateUserData(updateUserEmail, token, true).then().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Проверка статус кода при обновлении почты неавторизованного пользователя")
    @Description("Проверка статус кода при обновлении почты неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndUpdateUnauthorizedUserEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeInUpdateUnauthorizedUserEmail(user);
        updateUnauthorizedUserEmailForCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInUpdateUnauthorizedUserEmail(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUnauthorizedUserEmailForCheckStatusCode(UserAPI user){
        user.updateUserData(updateUserEmail, token, false).then().statusCode(401);
    }

    @Test
    @DisplayName("Проверка тела ответа при обновлении почты неавторизованного пользователя")
    @Description("Проверка тела ответа при обновлении почты неавторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkResponseAndUpdateUnauthorizedUserEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckResponseInUpdateUnauthorizedUserEmail(user);
        updateUnauthorizedUserEmailForCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckResponseInUpdateUnauthorizedUserEmail(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUnauthorizedUserEmailForCheckResponse(UserAPI user){
        user.updateUserData(updateUserEmail, token, false).then().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Проверка статус кода при обновлении почты занятым значением авторизованного пользователя")
    @Description("Проверка статус кода при обновлении почты занятым значением авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkStatusCodeAndUpdateUserExistEmail(){
        UserAPI user = new UserAPI();
        createUserForCheckStatusCodeInUpdateUserExistEmail(user);
        updateUserExistEmailForCheckStatusCode(user);
    }
    @Step("Создание пользователя")
    public void createUserForCheckStatusCodeInUpdateUserExistEmail(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserExistEmailForCheckStatusCode(UserAPI user){
        user.updateUserData(updateExistUserEmail, token, true).then().statusCode(403);
    }

    @Test
    @DisplayName("Проверка тела ответа при обновлении почты занятым значением авторизованного пользователя")
    @Description("Проверка тела ответа при обновлении почты занятым значением авторизованного пользователя в ручке PATCH /api/auth/user")
    public void checkResponseAndUpdateUserExistEmail(){
        UserAPI user = new UserAPI();
        createUserForResponseCodeInUpdateUserExistEmail(user);
        updateUserExistEmailForCheckResponse(user);
    }
    @Step("Создание пользователя")
    public void createUserForResponseCodeInUpdateUserExistEmail(UserAPI user){
        token = user.createUser(userBodyForCreate).then().extract().path("accessToken");
    }
    @Step("Обновление почты пользователя")
    public void updateUserExistEmailForCheckResponse(UserAPI user){
        user.updateUserData(updateExistUserEmail, token, true).then().body("message", equalTo("User with such email already exists"));
    }

    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}
