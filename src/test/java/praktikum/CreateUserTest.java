package praktikum;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends APITesting {

    @Before
    public void setUp(){
        RestAssured.baseURI = url;
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка статус кода и тела ответа ручки POST /api/auth/register")
    public void checkStatusCodeAndResponseCreateUniqueUser(){
        userBody = new User(email, password, name);
        UserAPI user = new UserAPI();
        token = user.createUser(userBody).then().statusCode(200).and().assertThat().body("success", equalTo(true)).extract().response().path("accessToken");
    }


    @Test
    @DisplayName("Создание 2 одинаковых пользователей для проверки статус кода")
    @Description("Проверка статус кода и тела ответа при создании 2 одинаковых пользователей в ручке POST /api/auth/register")
    public void createDuplicateUserForCheckStatusCodeAndResponse(){
        UserAPI user = new UserAPI();
        token = createFirstEqualUserForCheckStatusCodeAndResponse(user);
        createSecondEqualUserAndCheckStatusCodeAndResponse(user);
    }
    @Step("Создание первого пользователя")
    public String createFirstEqualUserForCheckStatusCodeAndResponse(UserAPI user){
        userBody = new User(email, password, name);
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
        userBodyWithoutName = new User(email, password);
        UserAPI user = new UserAPI();
        token = user.createUser(userBodyWithoutName).then().statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields")).extract().path("accessToken");
    }


    @After
    public void deleteUser(){
        UserAPI user = new UserAPI();
        if(token != null){
            user.deleteUser(token);
        }
    }
}