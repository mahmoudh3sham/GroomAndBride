package com.groomandbride.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.groomandbride.R;
import com.groomandbride.data.body.UpdatePassBody;
import com.groomandbride.data.models.UpdatePassword;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.validators.PasswordUpdateValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends BaseActivity {

    @BindView(R.id.oldPassET)
    EditText oldPassET;
    @BindView(R.id.newPassET)
    EditText newPassET;
    @BindView(R.id.confirmNewPassET)
    EditText confirmNewPassET;
    @BindView(R.id.btnResetPass)
    Button btnResetPass;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.back_button_bar)
    ImageView back_button_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        back_button_bar.setOnClickListener(view -> finish());
    }

    @OnClick(R.id.btnResetPass)
    void onResetPassClicked(){
        if(!validate())
            return;

        String oldPass = oldPassET.getText().toString();
        String newPass = newPassET.getText().toString();
        String confirmNewPass = confirmNewPassET.getText().toString();

        resetPassword(oldPass, newPass, confirmNewPass);
    }

    private void resetPassword(String oldPass, String newPass, String confirmNewPass) {
        showProgress();
        UpdatePassBody updatePassBody = new UpdatePassBody(oldPass, confirmNewPass, newPass);
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<UpdatePassword> resUpdatePasswordCall = apiService.updatePassword(updatePassBody);
        resUpdatePasswordCall.enqueue(new Callback<UpdatePassword>() {
            @Override
            public void onResponse(Call<UpdatePassword> call, Response<UpdatePassword> response) {
                hideProgress();
                try {
                    if (response.code() == 200){
                        showNoteDialog("Done", response.body().getMessage());
                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            showNoteDialog("Error", String.valueOf(jsonObject.get("message")));
                        }else {
                            saveRefreshedToken(jsonObject);
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
            public void onFailure(Call<UpdatePassword> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }

    private boolean validate(){
        int errorId = PasswordUpdateValidator.validate(
                oldPassET.getText().toString(),
                newPassET.getText().toString(),
                confirmNewPassET.getText().toString());
        if(errorId != -1){
            //showMessage(errorId);
            showNoteDialog("Error", (String) getText(errorId));
            return false;
        }
        return true;
    }
}
