package com.groomandbride.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.groomandbride.R;
import com.groomandbride.data.models.Hall;
import com.groomandbride.utils.LanguageUtils;
import com.groomandbride.utils.SharedPrefsUtils;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

/**
 * Created by Mahmoud Hesham on 25/9/2019
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtils.changeLocToEnglish(newBase));
    }
/*
    protected void showMessage(String m) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

    protected void showMessage(int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }*/

    protected void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void showProgressHome(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }

    protected void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }


    protected void showProgress(){
        if(progressDialog == null) {
            createProgress();
        }
        if(!progressDialog.isShowing())
            progressDialog.show();
    }

    protected void hideProgress(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void createProgress(){
        progressDialog = new Dialog(this, R.style.CustomDialogTheme);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCancelable(false);

        ImageView imageView = progressDialog.getWindow().getDecorView().findViewById(R.id.progressBarImageGif);
        Glide.with(getApplicationContext())
                .asGif()
                .load(R.drawable.loading_gif)
                .into(imageView);
    }

    protected void showResponeErrorBasedCodeMessage(Response response){
        JSONObject jsonObject;
        try {
            jsonObject  = new JSONObject (response.errorBody().string());
            String userMessage = jsonObject.getString("message");
            //showMessage(userMessage);
            showNoteDialog("Oops", userMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void saveRefreshedToken(JSONObject jsonObject){
        try {
            String token = String.valueOf(jsonObject.getString("token"));
            SharedPrefsUtils.getInstance().saveAccessToken(token);
            //showMessage("Session Expired, Please try again!");
            showNoteDialog("Session Expired", "Please try again!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sessionExpiredLogout(){
        //session expired
        SharedPrefsUtils.getInstance().clearUser();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    protected void showLoginDialogAddFav(Hall.DataBean itemHall){
        LayoutInflater factory = LayoutInflater.from(this);
        View dialogView = factory.inflate(R.layout.custom_dialog_addfav, null);
        AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setView(dialogView);

        Button btnLogin = dialogView.findViewById(R.id.okBtn);
        Button btnCancel = dialogView.findViewById(R.id.cancelBtn);


        btnCancel.setOnClickListener(view -> mAlertDialog.dismiss());

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("hall", itemHall);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });

        mAlertDialog.show();
    }

    protected void showLoginDialogRate(Hall.DataBean itemHall){
        LayoutInflater factory = LayoutInflater.from(this);
        View dialogView = factory.inflate(R.layout.custom_dialog_loginrate, null);
        AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setView(dialogView);

        Button btnLogin = dialogView.findViewById(R.id.okBtn);
        Button btnCancel = dialogView.findViewById(R.id.cancelBtn);


        btnCancel.setOnClickListener(view -> mAlertDialog.dismiss());

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("hall", itemHall);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });

        mAlertDialog.show();
    }

    protected void showNoteDialog(String title, String note){
        LayoutInflater factory = LayoutInflater.from(this);
        View dialogView = factory.inflate(R.layout.custom_dialog_note, null);
        AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setView(dialogView);

        Button btnOk = dialogView.findViewById(R.id.okBtn);
        TextView titleTV = dialogView.findViewById(R.id.title);
        TextView subTitleTV = dialogView.findViewById(R.id.subTitle);

        titleTV.setText(title);
        subTitleTV.setText(note);

        btnOk.setOnClickListener(view -> {
            mAlertDialog.dismiss();
        });

        mAlertDialog.show();
    }

    protected void showNoteDialogFinishActivity(String title, String note){
        LayoutInflater factory = LayoutInflater.from(this);
        View dialogView = factory.inflate(R.layout.custom_dialog_note, null);
        AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setView(dialogView);


        Button btnOk = dialogView.findViewById(R.id.okBtn);
        TextView titleTV = dialogView.findViewById(R.id.title);
        TextView subTitleTV = dialogView.findViewById(R.id.subTitle);

        titleTV.setText(title);
        subTitleTV.setText(note);

        btnOk.setOnClickListener(view -> {
            finish();
            mAlertDialog.dismiss();
        });

        mAlertDialog.show();
    }

}
