package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.body.ReqFavoritesBody;
import com.groomandbride.data.models.FavoritesModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.view.adapters.FavHallsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends BaseActivity {
    private static final String TAG = "FavoritesActivity";
    @BindView(R.id.back_button_bar)
    ImageView back_button_bar;
    @BindView(R.id.recFavorites)
    RecyclerView recFavorites;
    @BindView(R.id.favTxt)
    TextView favTxt;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    FavHallsAdapter mHallsAdapter;
    LinearLayoutManager mHallsRecLayoutManager;
    int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        back_button_bar.setOnClickListener(view -> finish());

        initFavHallsRec();
        showProgress();
        getMyFavs(pageNum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*initFavHallsRec();
        getMyFavs(pageNum);*/
    }

    private void getMyFavs(int pageNum) {

        ReqFavoritesBody reqFavoritesBody = new ReqFavoritesBody(5, pageNum);
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<FavoritesModel> resFavoritesModelCall = apiService.getFavs(reqFavoritesBody);
        resFavoritesModelCall.enqueue(new Callback<FavoritesModel>() {
            @Override
            public void onResponse(Call<FavoritesModel> call, Response<FavoritesModel> response) {
                hideProgress();
                try {
                    if (response.code() == 200){
                        //initHallsRec(response.body().getData());
                        if (response.body().getData().size() > 0){
                            if (pageNum == 0){
                                mHallsAdapter.mHallsList.clear();
                                mHallsAdapter.notifyDataSetChanged();
                            }
                            recFavorites.setVisibility(View.VISIBLE);
                            favTxt.setVisibility(View.GONE);
                            int startIndex = mHallsAdapter.mHallsList.size();

                            for (FavoritesModel.DataBean hall : response.body().getData()){
                                if (hall != null){
                                    mHallsAdapter.mHallsList.add(hall);
                                }
                            }
                            mHallsAdapter.notifyItemRangeInserted(startIndex, 5);
                        }
                        else {
                            recFavorites.setVisibility(View.GONE);
                            favTxt.setVisibility(View.VISIBLE);
                            Log.i(TAG, "onResponse: No more halls found!");
                            //Toast.makeText(FavoritesActivity.this, "No more halls found!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            //showMessage(String.valueOf(jsonObject.get("message")));
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
            public void onFailure(Call<FavoritesModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }


    private void initFavHallsRec(){
        mHallsAdapter = new FavHallsAdapter(new ArrayList<>(), this, itemHall -> {
            //go to hall details
            Intent intent = new Intent(getApplicationContext(), HallDetailesActivity.class);
            intent.putExtra("hall", itemHall);
            intent.putExtra("flag", "fav");
            startActivity(intent);
        });
        mHallsRecLayoutManager = new LinearLayoutManager(FavoritesActivity.this);
        recFavorites.setLayoutManager(mHallsRecLayoutManager);
        recFavorites.setNestedScrollingEnabled(true);
        recFavorites.setHasFixedSize(true);
        recFavorites.setAdapter(mHallsAdapter);

        //Pagination
        recFavorites.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHallsRecLayoutManager != null && mHallsRecLayoutManager.findLastCompletelyVisibleItemPosition() == mHallsAdapter.mHallsList.size() - 1) {
                    //showProgress(progress_bar);
                    getMyFavs(pageNum);
                }
            }
        });
    }
}
