package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.groomandbride.R;
import com.groomandbride.data.models.ForgetPassword;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassActivity extends BaseActivity {

    @BindView(R.id.emailForgetPass)
    EditText emailForgetPass;
    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.backBtn)
    ImageView backBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    //private ArrayList<Hall.DataBean> mListFromSplash = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);

        //mListFromSplash = getIntent().getParcelableArrayListExtra("list");
    }

    @OnClick(R.id.btn_send)
    void onBtnSendClicked(){
        String email = emailForgetPass.getText().toString();
        if (email.isEmpty()){
            emailForgetPass.setError("Enter your email");
            emailForgetPass.requestFocus();
            return;
        }
        if (!isValidEmail(email)){
            //Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            showNoteDialog("Error", "Invalid email");
            return;
        }

        forgetPassReq(email);
    }

    @OnClick(R.id.backBtn)
    void onBtnBackClicked(){
        goToLogin();
    }
    private void forgetPassReq(String email) {
        showProgress();
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<ForgetPassword> rePasswordCall = apiService.forgetPassReq(email);
        rePasswordCall.enqueue(new Callback<ForgetPassword>() {
            @Override
            public void onResponse(Call<ForgetPassword> call, Response<ForgetPassword> response) {
                hideProgress();

                if (response.isSuccessful()){
                    //Toast.makeText(ForgetPassActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    showNoteDialog("Done", response.body().getMessage());
                    //goToLogin();
                }
                else if (response.code() == 400){
                    showResponeErrorBasedCodeMessage(response);
                    //goToLogin();
                }
            }

            @Override
            public void onFailure(Call<ForgetPassword> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }


    static boolean isValidEmail(String email){
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToLogin();
    }


    private void goToLogin(){
        Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
        //intent.putExtra("list", mListFromSplash);
        startActivity(intent);
        finish();
    }
}
