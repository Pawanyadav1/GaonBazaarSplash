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
import com.devrik.GaonBazaar.ShowMyProductActivity;

import java.util.ArrayList;

public class Add_Category_Adapter extends RecyclerView.Adapter<Add_Category_Adapter.ViewHolder>{

    Context context;
    ArrayList<CategoryModel> categoryModelArrayList;

    public Add_Category_Adapter(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
    }

    @Override
    public Add_Category_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_category,parent,false);
        Add_Category_Adapter.ViewHolder viewHolder=new Add_Category_Adapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Add_Category_Adapter.ViewHolder holder, int position) {

        CategoryModel  CategoryModel = categoryModelArrayList.get(position);


        if (!CategoryModel.equals("")) {
            holder.txtSeedName.setText(CategoryModel.getName());


            try {
                Glide.with(context).load(CategoryModel.getPath()+CategoryModel.getImage())
                        .placeholder(R.drawable.crop)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.seed2);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dgfadf", e.getMessage());

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG","RAM"+CategoryModel.getId());

                   // Toast.makeText(context,"found id"+CategoryModel.getId(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ShowMyProductActivity.class);
                    intent.putExtra("CAT_ID",CategoryModel.getId());
                    context.startActivity(intent);


                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView seed2;
        TextView txtSeedName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seed2=itemView.findViewById(R.id.seed2);
            txtSeedName=itemView.findViewById(R.id.txtSeedName);
        }
    }
}
