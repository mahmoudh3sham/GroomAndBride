package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.body.LoginBody;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.models.UserModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.SharedPrefsUtils;
import com.groomandbride.utils.validators.LoginValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email_login)
    EditText email_login;
    @BindView(R.id.pass_login)
    EditText pass_login;
    @BindView(R.id.forget_pass)
    TextView forget_pass;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.register_txt)
    TextView register_txt;

    Hall.DataBean itemHall;

    //private ArrayList<Hall.DataBean> mListFromSplash = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        ButterKnife.bind(this);

        //mListFromSplash = getIntent().getParcelableArrayListExtra("list");
        itemHall = getIntent().getParcelableExtra("hall");

    }

    @OnClick(R.id.backBtn)
    void onBtnBackClicked(){
        if (itemHall != null){
            Intent intent = new Intent(LoginActivity.this, HallDetailesActivity.class);
            intent.putExtra("hall", itemHall);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }
    @OnClick(R.id.forget_pass)
    void onForgetPassClicked(){
        Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
        //intent.putExtra("list", mListFromSplash);
        startActivity(intent);
    }
    @OnClick(R.id.register_txt)
    void onRegTextClicked(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("hall", itemHall);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void onLoginBtnClicked(){
        if(!validate())
            return;

        String userEmail = email_login.getText().toString();
        String userPass = pass_login.getText().toString();

        login(userEmail, userPass);
    }

    public void login(String userEmail, String userPass) {
        showProgress();
        LoginBody loginBody = new LoginBody(userEmail, userPass);

        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<UserModel> resLogin = apiService.userLogin(loginBody);
        resLogin.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                hideProgress();
                try {
                    if (response.code() == 200){
                        //SharedPrefsUtils.getInstance().clearUser();
                        Log.e("Tokeeee", "onResponse: " + response.body().getUser().getToken());
                        SharedPrefsUtils.getInstance().saveAccessToken(response.body().getUser().getToken());
                        SharedPrefsUtils.getInstance().saveUserObject(response.body());
                        SharedPrefsUtils.getInstance().saveUsername(response.body().getUser().getUserName());

                        //Hall.DataBean itemHall = getIntent().getParcelableExtra("hall");
                        if (itemHall != null){
                            Intent intent = new Intent(LoginActivity.this, HallDetailesActivity.class);
                            intent.putExtra("hall", itemHall);
                            startActivity(intent);
                            finish();
                        }else {
                            goToMain();
                        }

                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            showNoteDialog("Error", jsonObject.getString("message"));
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }

    private boolean validate(){
        int errorId = LoginValidator.validate(
                email_login.getText().toString().trim(),
                pass_login.getText().toString());
        if(errorId != -1){
            //showMessage(errorId);
            showNoteDialog("Error", (String) getText(errorId));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (itemHall != null){
            Intent intent = new Intent(LoginActivity.this, HallDetailesActivity.class);
            intent.putExtra("hall", itemHall);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }

    private void goToMain(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //intent.putExtra("list", mListFromSplash);
        startActivity(intent);
        finish();
    }
}
