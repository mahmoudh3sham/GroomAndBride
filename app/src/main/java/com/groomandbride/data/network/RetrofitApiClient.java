package com.groomandbride.data.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groomandbride.utils.SharedPrefsUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {
    public static final String BASE_URL = "https://hidden-ocean-87285.herokuapp.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            //saved user token
            Log.i("Token", "Barear " + SharedPrefsUtils.getInstance().getAccessToken());


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            //To log the response for testing
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor headerInterceptor = chain -> {
                Request.Builder builder = chain.request().newBuilder()
                        .header("authorization", "Barear " + SharedPrefsUtils.getInstance().getAccessToken())
                        .header("Content-Type", "application/json");
                return chain.proceed(builder.build());
            };

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(240, TimeUnit.SECONDS);
            client.writeTimeout(240, TimeUnit.SECONDS);
            client.readTimeout(240, TimeUnit.SECONDS);

            client.addInterceptor(interceptor).addInterceptor(headerInterceptor).build();



            retrofit = new Retrofit.Builder()
                    .client(client.build())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}