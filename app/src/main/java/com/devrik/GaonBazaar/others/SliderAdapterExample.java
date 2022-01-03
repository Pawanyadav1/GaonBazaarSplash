package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devrik.GaonBazaar.Model.SliderImageData;
import com.devrik.GaonBazaar.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    List<SliderImageData> sliderarraylist;

    public SliderAdapterExample(List<SliderImageData> getDataAdapter, Context context) {
        this.context = context;
        this.sliderarraylist = getDataAdapter;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliderimage, null);
        return new SliderAdapterVH(inflate);
    }



    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        SliderImageData model=sliderarraylist.get(position);

        Log.e("SliderAdapterExample", "onBindViewHolder: " +model.getPath()+model.getImage());

        try {
            viewHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(model.getPath()+model.getImage()).into(viewHolder.img);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getCount() {

        return sliderarraylist.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView img;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            this.itemView = itemView;
        }
    }

}