package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devrik.GaonBazaar.Model.CategoryModel;
import com.devrik.GaonBazaar.R;
import com.devrik.GaonBazaar.SeedDetailScreenActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Show_Category_Adapter extends RecyclerView.Adapter<Show_Category_Adapter.ViewHolder> {
    Context context;
    ArrayList<CategoryModel> categoryModelArrayList;

    public Show_Category_Adapter(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_category,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel  CategoryModel = categoryModelArrayList.get(position);
        if (!CategoryModel.equals("")) {
            holder.txtSeedName.setText(CategoryModel.getName());


            try {
                Log.e("dkjh", CategoryModel.getPath());
                Log.e("dkjh", CategoryModel.getImage());
                Glide.with(context).load(CategoryModel.getPath()+CategoryModel.getImage())
                        .placeholder(R.drawable.spry).override(250, 250)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.seed2);

                holder.cate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("TAG","RAM"+CategoryModel.getId());

                        //Toast.makeText(context,"found id"+CategoryModel.getId(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,SeedDetailScreenActivity.class);
                        intent.putExtra("CAT_ID",CategoryModel.getId());
                        context.startActivity(intent);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dgfadf", e.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView seed2;
        TextView txtSeedName;
        MaterialCardView cate1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seed2=itemView.findViewById(R.id.seed2);
            txtSeedName=itemView.findViewById(R.id.txtSeedName);
            cate1=itemView.findViewById(R.id.cate1);
        }
    }
}
