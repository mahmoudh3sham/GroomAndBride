package com.groomandbride.view.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.groomandbride.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> mImgs;

    public SliderAdapter(Context context , List<String> mImgs) {
        this.context = context;
        this.mImgs = mImgs;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        Drawable mDefaultBackground = context.getResources().getDrawable(R.drawable.logo_low_zoom); //main logo

        if (!mImgs.isEmpty()){
            Glide.with(context)
                    .load(mImgs.get(position))
                    //.load("https://www.hallwines.com/media/gene-cms/h/a/hall-tile-2-lg.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_low_zoom)
                    .error(mDefaultBackground)
                    .into(viewHolder.imageView);
        }else {
            viewHolder.imageView.setImageDrawable(mDefaultBackground);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Clicked Id: " + bannerId, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mImgs.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_slider);
            //textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
