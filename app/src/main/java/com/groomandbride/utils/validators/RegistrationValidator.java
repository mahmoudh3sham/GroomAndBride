package com.groomandbride.utils.validators;


import com.groomandbride.R;

/**
 * Created by Mahmoud Hesham
 */
public class RegistrationValidator extends Validator {
    public static int validate(String name,
                                  String email,
                                  String pass){
        if(name.isEmpty())
            return R.string.error_empty_name;

        if(email.isEmpty())
            return R.string.error_empty_email;

        if(!isValidEmail(email))
            return R.string.error_email;

        if(pass.isEmpty())
            return R.string.error_empty_password;

        if(pass.length() < 6)
            return R.string.error_password;

        return -1;
    }
}
