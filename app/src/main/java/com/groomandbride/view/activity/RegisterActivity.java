package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.groomandbride.R;
import com.groomandbride.data.body.LoginBody;
import com.groomandbride.data.body.RegisterBody;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.models.RegisterModel;
import com.groomandbride.data.models.UserModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.SharedPrefsUtils;
import com.groomandbride.utils.validators.RegistrationValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.userNameReg)
    EditText userNameReg;
    @BindView(R.id.emailReg)
    EditText emailReg;
    @BindView(R.id.passReg)
    EditText passReg;
    @BindView(R.id.backRegBtn)
    ImageView backRegBtn;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    //private ArrayList<Hall.DataBean> mListFromSplash = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        ButterKnife.bind(this);

        //mListFromSplash = getIntent().getParcelableArrayListExtra("list");

    }

    @OnClick(R.id.backRegBtn)
    void onBackPress(){
        goToLogin();
    }

    @OnClick(R.id.btnRegister)
    void onRegBtnClicked(){
        if(!validate())
            return;

        String userName = userNameReg.getText().toString();
        String userEmail = emailReg.getText().toString();
        String userPass = passReg.getText().toString();

        registerNewUser(userName, userEmail, userPass);
    }

    private void registerNewUser(String userName, String userEmail, String userPass) {
        showProgress();
        RegisterBody registerBody = new RegisterBody(userEmail, userName, userPass);
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<RegisterModel> resRegisterModelCall = apiService.userRegister(registerBody);

        resRegisterModelCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                hideProgress();
                try {
                    if (response.code() == 200){
                        //Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        goToHomeAsLoggedIn(userEmail, userPass);
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
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }

    private void goToHomeAsLoggedIn(String userEmail, String userPass) {
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

                        Hall.DataBean itemHall = getIntent().getParcelableExtra("hall");
                        if (itemHall != null){
                            Intent intent = new Intent(RegisterActivity.this, HallDetailesActivity.class);
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
        int errorId = RegistrationValidator.validate(
                userNameReg.getText().toString(),
                emailReg.getText().toString().trim(),
                passReg.getText().toString().trim());
        if(errorId != -1){
            showNoteDialog("Error", (String) getText(errorId));
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToLogin();
    }

    private void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        //intent.putExtra("list", mListFromSplash);
        startActivity(intent);
        finish();
    }
    private void goToMain(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        //intent.putExtra("list", mListFromSplash);
        startActivity(intent);
        finish();
    }
}
