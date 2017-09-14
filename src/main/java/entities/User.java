package entities;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Users")
public class User extends Entity {
    private int id;
    private String login;
    private String password;

    @Id
    @SequenceGenerator(name = "seq_gen_user", sequenceName = "Users_UserID_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq_gen_user")
    @Column(name = "UserID", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Login", nullable = false, unique = true)
    public String getLogin() {
        return login;
    }

    void setLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login must be specified");
        }
        this.login = login;
    }

    @Column(name = "Password", nullable = false)
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
