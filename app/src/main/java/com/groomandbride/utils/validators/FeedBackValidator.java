package com.groomandbride.utils.validators;


import com.groomandbride.R;

/**
 * Created by Mahmoud Hesham
 */
public class FeedBackValidator extends Validator {
    public static int validate(String email,
                                  String textMsg){

        if(email.isEmpty())
            return R.string.error_empty_email;

        if(!isValidEmail(email))
            return R.string.error_email;

        if(textMsg.isEmpty())
            return R.string.error_empty_msg;

        return -1;
    }
}
