package sn.naavetane.backend.models;

public class UpdatePasswordDTO {
    private String login;
    private String holdPassword;
    private String password;

    public UpdatePasswordDTO() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHoldPassword() {
        return holdPassword;
    }

    public void setHoldPassword(String holdPassword) {
        this.holdPassword = holdPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
