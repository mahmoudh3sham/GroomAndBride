package com.groomandbride.data.body;

public class UpdatePassBody {
    private String userPassword;
    private String rePassword;
    private String newPassword;

    public UpdatePassBody(String userPassword, String rePassword, String newPassword) {
        this.userPassword = userPassword;
        this.rePassword = rePassword;
        this.newPassword = newPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
