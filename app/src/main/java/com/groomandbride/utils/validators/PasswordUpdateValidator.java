package com.groomandbride.utils.validators;


import com.groomandbride.R;

/**
 * Created by Mahmoud Hesham
 */
public class PasswordUpdateValidator extends Validator {
    public static int validate(String oldPass,
                               String newPass,
                               String renewPass){

        if(oldPass.isEmpty())
            return R.string.error_empty_password;

        if(newPass.isEmpty())
            return R.string.error_empty_new_password;

        if(newPass.length() < 6)
            return R.string.error_password;

        if(!newPass.matches(renewPass))
            return R.string.error_conflict_pass;

        return -1;
    }
}
