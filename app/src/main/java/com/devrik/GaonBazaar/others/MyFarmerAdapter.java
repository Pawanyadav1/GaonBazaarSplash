package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devrik.GaonBazaar.Model.MyFarmerModel;
import com.devrik.GaonBazaar.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFarmerAdapter extends RecyclerView.Adapter<MyFarmerAdapter.ViewHolder> {
    Context context;
    ArrayList<MyFarmerModel> myFarmerModelArrayList;

    public MyFarmerAdapter(Context context, ArrayList<MyFarmerModel> myfarmerModelArrayList) {
        this.context = context;
        this.myFarmerModelArrayList = myfarmerModelArrayList;

    }
    @Override
    public MyFarmerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myfarmers,parent,false);
        MyFarmerAdapter.ViewHolder viewHolder=new MyFarmerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFarmerAdapter.ViewHolder holder, int position) {

        MyFarmerModel  farmerModel = myFarmerModelArrayList.get(position);
        //Picasso.get().load(farmerModel.getPath()+farmerModel.getImage()).into(holder.seed_img);
       // USERID = SharedHelper.getKey(context,APPCONSTANT.id);


        if (!farmerModel.equals("")) {
            holder.txt_name.setText(farmerModel.getName());
            holder.txt_mob.setText(farmerModel.getPhone());
            holder.txt_address.setText(farmerModel.getVillage());
            holder.txt_Parent_name.setText(farmerModel.getParent_name());


            try {
                Log.e("fjk", farmerModel.getPath()+"");
               Log.e("fjk", farmerModel.getImage()+"");

                Glide.with(context).load(farmerModel.getPath()+farmerModel.getImage())
                      // .placeholder(R.drawable.seeds).override(50, 50)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img_profile);


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dgfadf", e.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return myFarmerModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img_profile;
        TextView txt_name,txt_mob,txt_address,txt_Parent_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_profile=itemView.findViewById(R.id.img_profile);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_mob=itemView.findViewById(R.id.txt_mob);
            txt_address=itemView.findViewById(R.id.txt_address);
            txt_Parent_name=itemView.findViewById(R.id.txt_Parent_name);

        }
    }

}
