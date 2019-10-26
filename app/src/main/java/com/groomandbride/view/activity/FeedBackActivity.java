package com.groomandbride.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.models.FeedBackModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.validators.FeedBackValidator;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.emailFeedBack)
    EditText emailFeedBack;
    @BindView(R.id.msgFeedBack)
    EditText msgFeedBack;
    @BindView(R.id.btnSendFeedBack)
    Button btnSendFeedBack;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    Toolbar toolbar;
    ImageView backBtn;
    TextView mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

        backBtn = findViewById(R.id.back_button_bar);
        mTitleBar = findViewById(R.id.toolbar_title_back);
        mTitleBar.setText("Feedback");
        setSupportActionBar(toolbar);
        backBtn.setOnClickListener(view -> finish());
    }

    @OnClick(R.id.btnSendFeedBack)
    void onSendFeedbackClicked(){
        if (!validate())
            return;

        String email = emailFeedBack.getText().toString();
        String msg = msgFeedBack.getText().toString();

        sendFeedBack(email, msg);
    }

    private void sendFeedBack(String email, String msg) {
        showProgress();
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<FeedBackModel> resFeedBackModelCall = apiService.sendFeedBack(email, msg);
        resFeedBackModelCall.enqueue(new Callback<FeedBackModel>() {
            @Override
            public void onResponse(Call<FeedBackModel> call, Response<FeedBackModel> response) {
                hideProgress();
                if (response.isSuccessful()){
                    //showMessage(response.body().getMessage());
                    showNoteDialog("Done", response.body().getMessage());
                    msgFeedBack.setText("");
                }
            }

            @Override
            public void onFailure(Call<FeedBackModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }


    private boolean validate(){
        int errorId = FeedBackValidator.validate(
                emailFeedBack.getText().toString(),
                msgFeedBack.getText().toString());
        if(errorId != -1){
            //showMessage(errorId);
            showNoteDialog("Error", (String) getText(errorId));
            return false;
        }
        return true;
    }
}
