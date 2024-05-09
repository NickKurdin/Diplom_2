package praktikum;

public class APITesting {
    public final String url = "https://stellarburgers.nomoreparties.site";
    public User userBody;
    public User userBodyWithoutName;
    public final String name = "kikkex12345678";
    public final String email = "kikkex12345678@yandex.ru";
    public final String password = "kikkex12345678";
    public String token;
    public final String incorrectPassword = "kikkex12345678___000";
    public String orderBody;
    public String firstIngredient;
    public String secondIngredient;
    public final String orderBodyWithoutIngredients = "{\n\"ingredients\": []\n}";
    public String orderBodyWithIncorrectIngredients;
    public final String updateUserName = "{\"name\":\"nickex\"}";
    public final String updateExistUserEmail = "{\"email\":\"nickex@yandex.ru\"}";
    public final String updateUserEmail = "{\"email\":\"nikkex12345678000@yandex.ru\"}";

}
