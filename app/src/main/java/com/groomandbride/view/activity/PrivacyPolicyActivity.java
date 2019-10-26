package com.groomandbride.view.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.models.PrivacyPolicyTermsModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;

import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends BaseActivity {

    TextView privacyTv;
    ProgressBar progressBar;
    Toolbar toolbar;
    ImageView backBtn;
    TextView mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        privacyTv = findViewById(R.id.privacyText);
        progressBar = findViewById(R.id.progress_bar);

        toolbar = findViewById(R.id.app_bar_back);
        backBtn = findViewById(R.id.back_button_bar);
        mTitleBar = findViewById(R.id.toolbar_title_back);
        mTitleBar.setText("Privacy Policy");
        setSupportActionBar(toolbar);

        backBtn.setOnClickListener(view -> finish());

        getPrivacy();
    }

    private void getPrivacy() {
        showProgress();
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<PrivacyPolicyTermsModel> resPrivacy = apiService.getPrivacyPolicy("privacy");
        resPrivacy.enqueue(new Callback<PrivacyPolicyTermsModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyTermsModel> call, Response<PrivacyPolicyTermsModel> response) {
                hideProgress();
                privacyTv.setText(Html.fromHtml(response.body().getData().getText()));
            }

            @Override
            public void onFailure(Call<PrivacyPolicyTermsModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }
}
