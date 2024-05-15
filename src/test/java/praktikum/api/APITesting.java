package praktikum.api;

import praktikum.testdata.User;
import java.util.Arrays;
import java.util.List;

public class APITesting {
    public final String name = "kikkex12345678";
    public final String email = "kikkex12345678@yandex.ru";
    public final String password = "kikkex12345678";
    public String token;
    public final String incorrectPassword = "kikkex12345678___000";
    public OrdersAPI order = new OrdersAPI();
    public String firstIngredient = order.getIngredientsForOrder().then().extract().path("data[0]._id");
    public String secondIngredient = order.getIngredientsForOrder().then().extract().path("data[1]._id");;
    public List<String> orderBodyWithoutIngredients = java.util.Collections.emptyList();
    public List<String> incorrectIngredients = Arrays.asList("123456", "654321");
    public final String updateUserName = "nickex";
    public final String updateExistUserEmail = "nickex@yandex.ru";
    public final String updateUserEmail = "nikkex12345678000@yandex.ru";

}
