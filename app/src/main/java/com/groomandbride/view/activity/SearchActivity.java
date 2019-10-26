package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.body.ReqSearchHallBody;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.view.adapters.HallsAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.searchET)
    EditText searchET;
    @BindView(R.id.searchBtn)
    TextView searchBtn;
    @BindView(R.id.recSearchRes)
    RecyclerView recSearchRes;
    @BindView(R.id.searchTxt)
    TextView searchTxt;
    @BindView(R.id.back_button_bar)
    ImageView back_button_bar;

    HallsAdapter mHallsAdapter;
    LinearLayoutManager mHallsRecLayoutManager;
    int pageNum = 0;
    String searchedHallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        back_button_bar.setOnClickListener(view -> finish());

        initHallsRec();

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchET.getText().toString().isEmpty()){
                    searchTxt.setText(R.string.search_def);
                    recSearchRes.setVisibility(View.GONE);
                    searchTxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.searchBtn)
    void onSearchClicked(){
        //hide keyboard
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchedHallName = searchET.getText().toString();
        if (searchedHallName.isEmpty()){
            searchET.setError("Enter hall name");
            searchET.requestFocus();
            return;
        }

        mHallsAdapter.mHallsList.clear();
        mHallsAdapter.notifyDataSetChanged();
        pageNum = 0;

        showProgress();
        getSearchRes(searchedHallName, pageNum);
    }

    private void getSearchRes(String searchedHallName, int pageNum) {
        ReqSearchHallBody reqSearchHallBody = new ReqSearchHallBody(searchedHallName, 5, pageNum);
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<Hall> resSearch = apiService.getSearchRes(reqSearchHallBody);
        resSearch.enqueue(new Callback<Hall>() {
            @Override
            public void onResponse(Call<Hall> call, Response<Hall> response) {
                hideProgress();
                if (response.code() == 200){
                    //initHallsRec(response.body().getData());
                    searchTxt.setVisibility(View.GONE);
                    recSearchRes.setVisibility(View.VISIBLE);

                    if (response.body().isResult()){
                        if (pageNum == 0){
                            mHallsAdapter.mHallsList.clear();
                            mHallsAdapter.notifyDataSetChanged();
                        }
                        int startIndex = mHallsAdapter.mHallsList.size();
                        mHallsAdapter.mHallsList.addAll(response.body().getData());
                        mHallsAdapter.notifyItemRangeInserted(startIndex, 5);
                    }else {
                        //Toast.makeText(SearchActivity.this, "No more halls found!", Toast.LENGTH_SHORT).show();
                        Log.i("GB", "onResponse: No more halls found!");
                    }
                }else {
                    //no halls found with this name
                    try {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        String userMessage = jsonObject.getString("message");
                        searchTxt.setText(userMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Hall> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });

    }


    private void initHallsRec(){
        mHallsAdapter = new HallsAdapter(new ArrayList<>(), this, itemHall -> {
            //go to hall details
            Intent intent = new Intent(getApplicationContext(), HallDetailesActivity.class);
            intent.putExtra("hall", itemHall);
            startActivity(intent);
        });
        mHallsRecLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recSearchRes.setLayoutManager(mHallsRecLayoutManager);
        recSearchRes.setNestedScrollingEnabled(true);
        recSearchRes.setHasFixedSize(true);
        recSearchRes.setAdapter(mHallsAdapter);

        //Pagination
        recSearchRes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHallsRecLayoutManager != null && mHallsRecLayoutManager.findLastCompletelyVisibleItemPosition() == mHallsAdapter.mHallsList.size() - 1) {
                    getSearchRes(searchedHallName, ++pageNum);
                }
            }
        });
    }
}
