package praktikum;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    public String userBody = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\",\n\"name\": \"nikkex12345678\"\n}";
    public String token;
    public String userBodyWithoutName = "{\n\"email\": \"nikkex12345678@yandex.ru\",\"password\": \"nikkex12345678\"\n}";

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка статус кода ручки POST /api/auth/register")
    public void checkStatusCodeCreateUniqueUser(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().statusCode(200).extract().response().path("accessToken");
    }

    @Test
    @DisplayName("Проверка тела ответа при создании уникального пользователя")
    @Description("Проверка наличия значения true в ответе ручки POST /api/auth/register")
    public void checkBodyResponseCreateUniqueUser(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().assertThat().body("success", equalTo(true)).extract().response().path("accessToken");
    }


    @Test
    @DisplayName("Создание 2 одинаковых пользователей для проверки статус кода")
    @Description("Проверка статус кода при создании 2 одинаковых пользователей в ручке POST /api/auth/register")
    public void createDuplicateUserForCheckStatusCode(){
        UserAPI user = new UserAPI();
        token = createFirstEqualUserForCheckStatusCode(user);
        int statusCode = createSecondEqualUserAndCheckStatusCode(user);
        checkSecondUserDoesNotCreateForCheckStatusCode(statusCode);
    }
    @Step("Создание первого пользователя")
    public String createFirstEqualUserForCheckStatusCode(UserAPI user){
        return user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание второго пользователя и получение статус кода")
    public int createSecondEqualUserAndCheckStatusCode(UserAPI user){
        int statusCode = user.createUser(userBody).statusCode();
        return statusCode;
    }
    @Step("Проверить, что второй пользователь не создаётся")
    public void checkSecondUserDoesNotCreateForCheckStatusCode(int statusCode){
        Assert.assertTrue(statusCode == 403);
    }

    @Test
    @DisplayName("Создание 2 одинаковых пользователей для проверки тела ответа")
    @Description("Проверка тела ответа при создании 2 одинаковых пользователей в ручке POST /api/auth/register")
    public void createDuplicateUserForCheckResponse(){
        UserAPI user = new UserAPI();
        token = createFirstEqualUserForCheckResponse(user);
        createSecondEqualUserAndCheckResponse(user);
    }
    @Step("Создание первого пользователя")
    public String createFirstEqualUserForCheckResponse(UserAPI user){
        return user.createUser(userBody).then().extract().path("accessToken");
    }
    @Step("Создание второго пользователя и получение тела ответа")
    public void createSecondEqualUserAndCheckResponse(UserAPI user){
        user.createUser(userBody).then().assertThat().body("message", equalTo("User already exists"));
    }


    @Test
    @DisplayName("Создание пользователя без имени для проверки статус кода")
    @Description("Проверка невозможности создать пользователя без обязательного поля в ручке POST /api/auth/register")
    public void createUserWithoutNameForCheckStatusCode(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBodyWithoutName).then().statusCode(403).extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя без имени для проверки статус кода")
    @Description("Проверка невозможности создать пользователя без обязательного поля в ручке POST /api/auth/register")
    public void createUserWithoutNameForCheckResponse(){
        UserAPI user = new UserAPI();
        token = user.createUser(userBodyWithoutName).then().assertThat().body("message", equalTo("Email, password and name are required fields")).extract().path("accessToken");
    }


    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}