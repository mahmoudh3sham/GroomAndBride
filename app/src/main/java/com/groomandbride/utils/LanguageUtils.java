package com.groomandbride.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Mahmoud Hesham
 */

public class LanguageUtils {

  public static Context changeLocToEnglish(Context context) {
    Resources res = context.getResources();
    DisplayMetrics dm = res.getDisplayMetrics();
    Configuration config = res.getConfiguration();


    Locale locale = new Locale("en");


    Locale.setDefault(locale);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      setSystemLocale(config, locale);
    } else {
      setSystemLocaleLegacy(config, locale);
    }
    return context.createConfigurationContext(config);
  }

  private static void setSystemLocaleLegacy(Configuration config, Locale locale) {
    config.locale = locale;
  }

  @TargetApi(Build.VERSION_CODES.N)
  private static void setSystemLocale(Configuration config, Locale locale) {
    config.setLocale(locale);
  }
}
