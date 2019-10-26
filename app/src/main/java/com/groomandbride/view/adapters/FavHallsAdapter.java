package com.groomandbride.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.groomandbride.R;
import com.groomandbride.data.models.FavoritesModel;
import com.groomandbride.data.models.RemoveFromFavModel;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.SharedPrefsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavHallsAdapter extends RecyclerView.Adapter<FavHallsAdapter.HallsViewHolder> {

    public List<FavoritesModel.DataBean> mHallsList;
    private Context context;

    public FavHallsAdapter(List<FavoritesModel.DataBean> mHallsList, Context context, OnItemClickListener listener){
        this.mHallsList = mHallsList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HallsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fav_hall, viewGroup, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        HallsViewHolder rcv = new HallsViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final HallsViewHolder holder, final int pos) {

        final FavoritesModel.DataBean itemHall = mHallsList.get(pos);

        Drawable mDefaultBackground = context.getResources().getDrawable(R.drawable.logo_low_zoom); //main logo
        List<String> itemListImg = itemHall.getHallImage();
        if (!itemListImg.isEmpty()){
            Glide.with(context)
                    .load(itemListImg.get(0))
                    //.load("https://www.hallwines.com/media/gene-cms/h/a/hall-tile-2-lg.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_low_zoom)
                    .error(mDefaultBackground)
                    .into(holder.imgHall);
        }else {
            holder.imgHall.setImageDrawable(mDefaultBackground);
        }


        holder.hallNameItem.setText(itemHall.getHallName());
        holder.priceHallItem.setText(String.valueOf(itemHall.getHallPrice()));
        holder.rateNumItem.setText(String.valueOf(itemHall.getHallsRatingCounter()));



        LayerDrawable layerDrawable = (LayerDrawable) holder.ratingBar.getProgressDrawable();

        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), Color.GRAY);  // Empty star
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), context.getResources().getColor(R.color.goldcolor)); // Partial star
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), context.getResources().getColor(R.color.goldcolor));
        holder.ratingBar.setRating((float) itemHall.getHallsAverageRating());


        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(itemHall); //listener
        });

        holder.imgFavOn.setOnClickListener(view ->
                removeFromFavReq(itemHall.get_id(), pos) //remove from api and recycler
        );

    }

    @Override
    public int getItemCount() {
        return mHallsList.size();
    }

    private void removeItem(int position){
        mHallsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mHallsList.size());
    }

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FavoritesModel.DataBean itemHall);
    }

    class HallsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHall;
        ImageView imgFavOn;
        TextView hallNameItem, priceHallItem, rateNumItem;
        RatingBar ratingBar;

        HallsViewHolder(View view){
            super(view);
            imgHall = view.findViewById(R.id.imgHall);
            imgFavOn = view.findViewById(R.id.imgFavOn);
            hallNameItem = view.findViewById(R.id.hallNameItem);
            priceHallItem = view.findViewById(R.id.priceHallItem);
            rateNumItem = view.findViewById(R.id.rateNumItem);
            ratingBar = view.findViewById(R.id.ratingBar);

        }
    }



    private void removeFromFavReq(String hallId, int pos) {
        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<RemoveFromFavModel> resRemoveFromFavModelCall = apiService.removeFromFav(hallId);
        resRemoveFromFavModelCall.enqueue(new Callback<RemoveFromFavModel>() {
            @Override
            public void onResponse(Call<RemoveFromFavModel> call, Response<RemoveFromFavModel> response) {
                try {
                    if (response.code() == 200){
                        removeItem(pos);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONObject jsonObject  = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")){
                            Toast.makeText(context, String.valueOf(jsonObject.get("message")), Toast.LENGTH_SHORT).show();
                        }else {
                            //session expired
                            /*SharedPrefsUtils.getInstance().clearUser();
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();*/
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
                Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void saveRefreshedToken(JSONObject jsonObject){
        try {
            String token = jsonObject.getString("token");
            SharedPrefsUtils.getInstance().saveAccessToken(token);
            Toast.makeText(context, "Session Expired, Please try again!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
