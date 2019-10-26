package com.groomandbride.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.groomandbride.R;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends BaseActivity {

    private ArrayList<Hall.DataBean> mHomeAllList = new ArrayList<>();
    HashMap<String,Integer> hashMapHalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //splash();
        loadAllHalls();
    }

    private void splash(){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
            finish();
        }, 2000);
    }

    private void loadAllHalls() {

        hashMapHalls = new HashMap<>();
        hashMapHalls.put("limit", 10);
        hashMapHalls.put("offset", 0);

        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<Hall> resHallCall = apiService.getAllHalls(hashMapHalls);
        resHallCall.enqueue(new Callback<Hall>() {
            @Override
            public void onResponse(Call<Hall> call, Response<Hall> response) {
                if (response.code() == 200){
                    if (response.body().getData().size() != 0){
                        mHomeAllList.addAll(response.body().getData());

                        Intent intent = new Intent(SplashScreen.this, WelcomeActivity.class);
                        intent.putExtra("list", mHomeAllList);
                        startActivity(intent);
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<Hall> call, Throwable t) {
                Log.e("Error", "onFailure: " + t.getMessage() );
                showNoteDialogFinishActivity("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }
}
