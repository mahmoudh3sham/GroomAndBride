package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.models.UpdateProfile;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.SharedPrefsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.userNameProfile)
    EditText userNameProfile;
    @BindView(R.id.emailProfile)
    EditText emailProfile;
    @BindView(R.id.changePassTxt)
    TextView changePassTxt;
    @BindView(R.id.btnApplyChanges)
    Button btnApplyChanges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        userNameProfile.setText(SharedPrefsUtils.getInstance().getUsername());
        emailProfile.setText(SharedPrefsUtils.getInstance().getUserObject().getUser().getUserEmail());

        ImageView backBtn = findViewById(R.id.back_button_bar);
        backBtn.setOnClickListener(view -> finish());
    }

    @OnClick(R.id.changePassTxt)
    void onChangePassClicked(){
        startActivity(new Intent(getApplicationContext(), ChangePassActivity.class));
    }

    @OnClick(R.id.btnApplyChanges)
    void onCbtnApplyChangesClicked(){
        String username = userNameProfile.getText().toString();
        if (username.isEmpty()){
            userNameProfile.requestFocus();
            userNameProfile.setError("Enter your username");
            return;
        }
        updateInfo(username);
    }

    private void updateInfo(String username) {
        showProgress();
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<UpdateProfile> resProfileCall = apiService.updateUsername(username);
        resProfileCall.enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                hideProgress();
                try {
                    if (response.code() == 200){
                        showNoteDialog("Done", response.body().getMessage());
                        SharedPrefsUtils.getInstance().saveUsername(response.body().getData());
                        userNameProfile.setText(response.body().getData());
                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        saveRefreshedToken(jsonObject);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }
}
