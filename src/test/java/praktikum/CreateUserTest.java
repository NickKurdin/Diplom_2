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

public class CreateUserTest{
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
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка статус кода и тела ответа ручки POST /api/auth/register")
    public void checkStatusCodeAndResponseCreateUniqueUser(){
        test.token = user.createUser(userBody).then().statusCode(200).and().assertThat().body("success", equalTo(true)).extract().response().path("accessToken");
    }


    @Test
    @DisplayName("Создание 2 одинаковых пользователей для проверки статус кода")
    @Description("Проверка статус кода и тела ответа при создании 2 одинаковых пользователей в ручке POST /api/auth/register")
    public void createDuplicateUserForCheckStatusCodeAndResponse(){
        test.token = createFirstEqualUserForCheckStatusCodeAndResponse(user);
        createSecondEqualUserAndCheckStatusCodeAndResponse(user);
    }
    @Step("Создание первого пользователя")
    public String createFirstEqualUserForCheckStatusCodeAndResponse(UserAPI user){
        return user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание второго пользователя, получение статус кода и тела ответа")
    public void createSecondEqualUserAndCheckStatusCodeAndResponse(UserAPI user){
        user.createUser(userBody).then().statusCode(403).and().assertThat().body("message", equalTo("User already exists"));
    }


    @Test
    @DisplayName("Создание пользователя без имени для проверки статус кода и тела ответа")
    @Description("Проверка невозможности создать пользователя без обязательного поля в ручке POST /api/auth/register")
    public void createUserWithoutNameForCheckStatusCodeAndStatusCode(){
        userBody = new User(test.email, test.password);
        test.token = user.createUser(userBody).then().statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields")).extract().path("accessToken");
    }


    @After
    public void deleteUser(){
        if(test.token != null){
            user.deleteUser(test.token);
        }
    }
}