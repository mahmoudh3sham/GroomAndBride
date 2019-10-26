package com.groomandbride.data.body;

public class RegisterBody {
    private String userEmail;
    private String userName;
    private String userPassword;

    public RegisterBody(String userEmail, String userName, String userPassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
