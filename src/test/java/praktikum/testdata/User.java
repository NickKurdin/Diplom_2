package praktikum.testdata;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User setEmailForBody(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public User setPasswordForBody(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User setNameForBody(String name) {
        this.name = name;
        return this;
    }
}
