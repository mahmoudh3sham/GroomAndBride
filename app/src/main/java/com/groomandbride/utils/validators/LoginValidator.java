package com.groomandbride.utils.validators;


import com.groomandbride.R;

/**
 * Created by Mahmoud Hesham
 */
public class LoginValidator extends Validator {
    public static int validate(String email,
                                  String pass){

        if(email.isEmpty())
            return R.string.error_empty_email;

        if(!isValidEmail(email))
            return R.string.error_email;

        if(pass.isEmpty())
            return R.string.error_empty_password;

        return -1;
    }
}
