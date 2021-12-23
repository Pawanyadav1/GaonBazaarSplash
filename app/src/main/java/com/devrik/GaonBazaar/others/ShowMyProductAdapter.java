package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devrik.GaonBazaar.Model.MyModel;
import com.devrik.GaonBazaar.R;
import com.devrik.GaonBazaar.SeedDetailScreenActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowMyProductAdapter extends RecyclerView.Adapter<ShowMyProductAdapter.ViewHolder> {
    Context context;
    ArrayList<MyModel> myModelArrayList;

    public ShowMyProductAdapter(Context context, ArrayList<MyModel> myModelArrayList) {
        this.context = context;
        this.myModelArrayList = myModelArrayList;
    }

    @Override
    public ShowMyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myproductshow,parent,false);

        ShowMyProductAdapter.ViewHolder viewHolder=new ShowMyProductAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowMyProductAdapter.ViewHolder holder, int position) {


        MyModel  myModel = myModelArrayList.get(position);
        Picasso.get().load(myModel.getPath()+myModel.getImage()).into(holder.seed_img);
        if (!myModel.equals("")) {
            holder.txtCommdity.setText(myModel.getCommdity());
            holder.txtvariety.setText(myModel.getVariety());
            holder.txtbrand.setText(myModel.getBrandName());
            holder.txtcompeny.setText(myModel.getCompany());
            holder.txtprice.setText(myModel.getPrice());
            holder.name.setText(myModel.getName());

            try {
                Log.e("sfdh", myModel.getPath()+"");
                Log.e("sfdh", myModel.getImage()+"");
                Glide.with(context).load(myModel.getPath()+myModel.getImage())
                        .placeholder(R.drawable.seeds).override(50, 50)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.seed_img);

                holder.btn_sell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SeedDetailScreenActivity.class);
                        context.startActivity(intent);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dgfadf",e.getMessage());

            }
        }

    }

    @Override
    public int getItemCount() {
        return myModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView seed_img;
        Button btn_sell,incriment,dcreement;
        TextView name,txtCommdity,txtvariety,txtbrand,txtcompeny,txtprice,text_quantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seed_img=itemView.findViewById(R.id.seed_img);
            txtCommdity=itemView.findViewById(R.id.txtCommdity);
            txtvariety=itemView.findViewById(R.id.txtvariety);
            txtbrand=itemView.findViewById(R.id.txtbrand);
            txtcompeny=itemView.findViewById(R.id.txtcompeny);
            name=itemView.findViewById(R.id.name);
            dcreement=itemView.findViewById(R.id.dcreement);
            incriment=itemView.findViewById(R.id.incriment);
            text_quantity=itemView.findViewById(R.id.text_quantity);
            txtprice=itemView.findViewById(R.id.txtprice);
            btn_sell=itemView.findViewById(R.id.btn_sell);


        }
    }
}


