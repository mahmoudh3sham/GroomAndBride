package com.groomandbride.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.groomandbride.data.models.MainCategory;
import com.groomandbride.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainCateAdapter extends RecyclerView.Adapter<MainCateAdapter.CategoryViewHolder> {

    List<MainCategory> mCategoriesList;
    Context context;

    public MainCateAdapter(List<MainCategory> mCategoriesList, Context context, OnItemClickListener listener){
        this.mCategoriesList = mCategoriesList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        CategoryViewHolder rcv = new CategoryViewHolder(layoutView);
        return rcv;
    }

    private int itemSelectedPos = 0;
    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int pos) {

        final MainCategory item = mCategoriesList.get(pos);

        holder.name.setText(item.getName());

        if (itemSelectedPos == pos){
            holder.name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.img.setImageDrawable(context.getResources().getDrawable(item.getImgDrawble2()));
        }else {
            holder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.img.setImageDrawable(context.getResources().getDrawable(item.getImgDrawble()));
        }

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(mCategoriesList.get(pos)); //click listener

            //for changing clicked item color
            itemSelectedPos = pos;
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return mCategoriesList.size();
    }



    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MainCategory itemMainCate);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;

        CategoryViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.nameMainCate);
            img = view.findViewById(R.id.imgMainCate);
        }
    }
}
