package com.groomandbride.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

//Language Helper class
public class MyContextWrapper extends ContextWrapper {

    // vars
    private Context context;

    public MyContextWrapper(Context context) {
        super(context);
        this.context = context;
    } // constructor

    @SuppressLint("ObsoleteSdkInt")
    public ContextWrapper wrap (String language)
    {
        Context mContext = context;
        Configuration configuration = context.getResources().getConfiguration();
        if (!language.isEmpty())
        {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                setSystemLocale(configuration, locale);
            }
            else {
                setSystemLocaleLegacy(configuration, locale);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                mContext = context.createConfigurationContext(configuration);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                mContext = context.createConfigurationContext(configuration);
            }
            else {
                context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
            }

        }
        return new MyContextWrapper(mContext);
    }

    private void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    } // set locale

    @TargetApi(Build.VERSION_CODES.N)
    private void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    } // set target api
}