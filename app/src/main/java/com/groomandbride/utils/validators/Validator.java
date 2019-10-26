package com.groomandbride.utils.validators;

/**
 * Created by Mahmoud Hesham
 */
public abstract class Validator {
    static boolean isAlpha(String str){
        return str.matches("^[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z ]*$");
        //return true;
    }

    static boolean isNumbersOnly(String str){
        return str.matches("^[0-9]+$");
    }

    static boolean isValidEmail(String email){
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    }

    static boolean isValidMobile(String mobile){
        return mobile.matches("^\\+?\\d+$");
    }
}
