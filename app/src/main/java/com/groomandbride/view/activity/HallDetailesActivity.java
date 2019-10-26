package com.groomandbride.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.groomandbride.R;
import com.groomandbride.data.models.AddToFavModel;
import com.groomandbride.data.models.FavoritesModel;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.models.RateModel;
import com.groomandbride.data.models.RemoveFromFavModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.SharedPrefsUtils;
import com.groomandbride.view.adapters.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.DrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HallDetailesActivity extends BaseActivity {

    @BindView(R.id.imageSlider)
    SliderView sliderView;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.rateNumItem)
    TextView rateNumItem;
    @BindView(R.id.hallPriceTV)
    TextView hallPriceTV;
    @BindView(R.id.hallName)
    TextView hallName;
    @BindView(R.id.hallDesc)
    TextView hallDesc;
    @BindView(R.id.hallAddress)
    TextView hallAddress;
    @BindView(R.id.hallSpecialOffer)
    TextView hallSpecialOffer;
    @BindView(R.id.callDetailesBtn)
    LinearLayout callDetailesBtn;
    @BindView(R.id.imgFavOff)
    ImageView imgFavOff;
    @BindView(R.id.imgFavOn)
    ImageView imgFavOn;
    @BindView(R.id.back_button_bar)
    ImageView back_button_bar;
    @BindView(R.id.locationMapBtn)
    LinearLayout locationMapBtn;

    ProgressBar progress_bar_dialog;

    View dialogView;
    AlertDialog mAlertDialog;

    Hall.DataBean itemHall;
    FavoritesModel.DataBean itemHallFav;

    List<String> mHallImgsList = new ArrayList<>();
    int rateNum = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_detailes);
        ButterKnife.bind(this);
        back_button_bar.setOnClickListener(view -> finish());

        if (getIntent().getStringExtra("flag") != null){
            //coming from fav
            itemHallFav = getIntent().getParcelableExtra("hall");
            //mHallImgsList = itemHall.getHallImage();
            //loadBannerSlider(mHallImgsList);
            loadBannerSlider(addStaticSliderImgs()); //comment this line when getting real imgs

            setupRatingBarColors(ratingBar);
            ratingBar.setRating((float) itemHallFav.getHallsAverageRating());
            rateNumItem.setText(String.valueOf(itemHallFav.getHallsRatingCounter()));
            hallPriceTV.setText(String.valueOf(itemHallFav.getHallPrice()) + " EGP");
            hallName.setText(itemHallFav.getHallName());
            hallDesc.setText(itemHallFav.getHallDescription());
            hallAddress.setText(itemHallFav.getHallAdress());
            hallSpecialOffer.setText(itemHallFav.getHallSpecialOffers());
            imgFavOff.setVisibility(View.GONE);
            imgFavOn.setVisibility(View.VISIBLE);
        }
        else {
            //coming from home or search
            itemHall = getIntent().getParcelableExtra("hall");
            //mHallImgsList = itemHall.getHallImage();
            //loadBannerSlider(mHallImgsList);
            loadBannerSlider(addStaticSliderImgs()); //comment this line when getting real imgs


            rateNumItem.setText(String.valueOf(itemHall.getHallsRatingCounter()));
            hallPriceTV.setText(String.valueOf(itemHall.getHallPrice()) + " EGP");
            hallName.setText(itemHall.getHallName());
            hallDesc.setText(itemHall.getHallDescription());
            hallAddress.setText(itemHall.getHallAdress());
            hallSpecialOffer.setText(itemHall.getHallSpecialOffers());

            setupRatingBarColors(ratingBar);
            ratingBar.setRating((float) itemHall.getHallsAverageRating());
        }


    }

    private void setupRatingBarColors(RatingBar ratingBar) {
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), Color.GRAY);  // Empty star
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), getResources().getColor(R.color.goldcolor)); // Partial star
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), getResources().getColor(R.color.goldcolor)); // Full star
    }

    @OnClick(R.id.locationMapBtn)
    void onLocationMapClicked(){
        //From Fav
        if (getIntent().getStringExtra("flag") != null){
            FavoritesModel.DataBean hall = getIntent().getParcelableExtra("hall");
            Intent intent = new Intent(HallDetailesActivity.this, MapLocationActivity.class);
            intent.putExtra("hall", hall);
            intent.putExtra("flag", "fav");
            startActivity(intent);
        }
        //From Home
        else{
            Hall.DataBean hall = getIntent().getParcelableExtra("hall");
            Intent intent = new Intent(HallDetailesActivity.this, MapLocationActivity.class);
            intent.putExtra("hall", hall);
            startActivity(intent);
        }
    }
    @OnClick(R.id.imgFavOff)
    void onImgFavOffClicked() {
        if (SharedPrefsUtils.getInstance().isUserLogged()){
            showProgress();
            String hallId;
            if (getIntent().getStringExtra("flag") != null){
                hallId = itemHallFav.get_id();
                addToFavReq(hallId);
            }else {
                hallId = itemHall.get_id();
                addToFavReq(hallId);
            }
        }else {
            showLoginDialogAddFav(itemHall);
        }


    }

    @OnClick(R.id.imgFavOn)
    void onImgFavOnClicked() {
        showProgress();
        String hallId;
        if (getIntent().getStringExtra("flag") != null){
            hallId = itemHallFav.get_id();
            removeFromFav(hallId);
        }else {
            hallId = itemHall.get_id();
            removeFromFav(hallId);
        }

    }

    @OnClick(R.id.ratingLina)
    void onRatingBarClicked() {
        if (SharedPrefsUtils.getInstance().isUserLogged()){
            showRateDialog();
        }else {
            showLoginDialogRate(itemHall);
        }

    }

    @OnClick(R.id.callDetailesBtn)
    void oncallDetailesBtnClicked() {
        if (getIntent().getStringExtra("flag") != null){
            callHall(itemHallFav.getHallPhoneNumber());
        }else {
            callHall(itemHall.getHallPhoneNumber());
        }

    }

    private void callHall(String hallPhoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + hallPhoneNumber));
        startActivity(intent);
    }

    private void showRateDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        dialogView = factory.inflate(R.layout.custom_dialog_rate, null);
        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setView(dialogView);

        RatingBar ratingBarDialog = dialogView.findViewById(R.id.ratingBarDialog);
        Button btnRate = dialogView.findViewById(R.id.rateBtn);
        Button btnCancel = dialogView.findViewById(R.id.cancelBtn);
        progress_bar_dialog = dialogView.findViewById(R.id.progress_bar_dialog);

        setupRatingBarColors(ratingBarDialog);

        ratingBarDialog.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            rateNum = (int) v;
            //ratingBarDialog.setRating((float) rateNum - 1);
            Log.e("Rate", "onRatingChanged: " + rateNum);
        });

        btnCancel.setOnClickListener(view -> mAlertDialog.dismiss());

        btnRate.setOnClickListener(view -> {
            if (getIntent().getStringExtra("flag") != null){
                addNewRate(itemHallFav.get_id(), rateNum);
            }else {
                addNewRate(itemHall.get_id(), rateNum);
            }

        });

        mAlertDialog.show();
    }

    private void addNewRate(String id, int rateNum) {
        showProgress();
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<RateModel> resRateModelCall = apiService.rateHall(id, rateNum);
        resRateModelCall.enqueue(new Callback<RateModel>() {
            @Override
            public void onResponse(Call<RateModel> call, Response<RateModel> response) {
                hideProgress();
                try{
                    if (response.code() == 200){
                        showNoteDialog("Done", response.body().getMessage());
                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            //showMessage(String.valueOf(jsonObject.get("message")));
                            showNoteDialog("Error", String.valueOf(jsonObject.get("message")));
                        }else {
                            if (jsonObject.has("token")){
                                saveRefreshedToken(jsonObject);
                            }
                        }
                    }
                    mAlertDialog.dismiss();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RateModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }

    private void addToFavReq(String hallId) {
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<AddToFavModel> resAddToFavModelCall = apiService.addToFav(hallId);
        resAddToFavModelCall.enqueue(new Callback<AddToFavModel>() {
            @Override
            public void onResponse(Call<AddToFavModel> call, Response<AddToFavModel> response) {
                try{
                    hideProgress();
                    if (response.code() == 200){
                        imgFavOff.setVisibility(View.GONE);
                        imgFavOn.setVisibility(View.VISIBLE);
                        showNoteDialog("Done", response.body().getMessage());
                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            //already in your favs
                            imgFavOff.setVisibility(View.GONE);
                            imgFavOn.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<AddToFavModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }


    private void removeFromFav(String hallId) {
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<RemoveFromFavModel> resRemoveFromFavModelCall = apiService.removeFromFav(hallId);
        resRemoveFromFavModelCall.enqueue(new Callback<RemoveFromFavModel>() {
            @Override
            public void onResponse(Call<RemoveFromFavModel> call, Response<RemoveFromFavModel> response) {
                try {
                    hideProgress();
                    if (response.code() == 200){
                        imgFavOff.setVisibility(View.VISIBLE);
                        imgFavOn.setVisibility(View.GONE);
                        showNoteDialog("Done", response.body().getMessage());
                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            //already in your favs
                            imgFavOff.setVisibility(View.VISIBLE);
                            imgFavOn.setVisibility(View.GONE);
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
            public void onFailure(Call<RemoveFromFavModel> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }


    private void loadBannerSlider(List<String> mHallImgsList) {
        SliderAdapter adapter = new SliderAdapter(getApplicationContext(), mHallImgsList);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private List<String> addStaticSliderImgs(){
        mHallImgsList.add("https://peasipl.com/wp-content/uploads/2017/05/wedding-hall.jpg");
        mHallImgsList.add("https://davidharman.files.wordpress.com/2011/04/banquet-hall.jpg");
        mHallImgsList.add("http://www.ornaresort.com.my/images/Rest2.jpg");
        mHallImgsList.add("https://apps.singaporebrides.com/pricelist/assets/2779_2084_1535_main.original.original.retina.jpg");
        return mHallImgsList;
    }

}
