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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.groomandbride.R;
import com.groomandbride.data.models.Hall;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

public class HallsAdapter extends RecyclerView.Adapter<HallsAdapter.HallsViewHolder> {

    public List<Hall.DataBean> mHallsList;
    private Context context;

    public HallsAdapter(List<Hall.DataBean> mHallsList, Context context, OnItemClickListener listener){
        this.mHallsList = mHallsList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HallsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hall, viewGroup, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        HallsViewHolder rcv = new HallsViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final HallsViewHolder holder, final int pos) {

        final Hall.DataBean itemHall = mHallsList.get(pos);

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

    }

    @Override
    public int getItemCount() {
        return mHallsList.size();
    }



    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Hall.DataBean itemHall);
    }

    class HallsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHall;
        TextView hallNameItem, priceHallItem, rateNumItem;
        RatingBar ratingBar;

        HallsViewHolder(View view){
            super(view);
            imgHall = view.findViewById(R.id.imgHall);
            hallNameItem = view.findViewById(R.id.hallNameItem);
            priceHallItem = view.findViewById(R.id.priceHallItem);
            rateNumItem = view.findViewById(R.id.rateNumItem);
            ratingBar = view.findViewById(R.id.ratingBar);

        }
    }
}
