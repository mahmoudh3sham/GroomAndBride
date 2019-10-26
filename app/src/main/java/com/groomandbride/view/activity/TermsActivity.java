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

public class TermsActivity extends BaseActivity {
    TextView termsTv;
    ProgressBar progressBar;
    Toolbar toolbar;
    ImageView backBtn;
    TextView mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        termsTv = findViewById(R.id.termsText);
        progressBar = findViewById(R.id.progress_bar);

        toolbar = findViewById(R.id.app_bar_back);
        backBtn = findViewById(R.id.back_button_bar);
        mTitleBar = findViewById(R.id.toolbar_title_back);
        mTitleBar.setText("Terms of Service");
        setSupportActionBar(toolbar);

        backBtn.setOnClickListener(view -> finish());

        getTerms();
    }

    private void getTerms() {
        showProgress();
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<PrivacyPolicyTermsModel> resPrivacy = apiService.getPrivacyPolicy("service");
        resPrivacy.enqueue(new Callback<PrivacyPolicyTermsModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyTermsModel> call, Response<PrivacyPolicyTermsModel> response) {
                hideProgress();
                termsTv.setText(Html.fromHtml(response.body().getData().getText()));
            }

            @Override
            public void onFailure(Call<PrivacyPolicyTermsModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }
}
