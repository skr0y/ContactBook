package model.entities;

public class User extends Entity {
    private int id;
    private String login;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    void setLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login must be specified");
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password must be specified");
        }
        this.password = password;
    }
}
