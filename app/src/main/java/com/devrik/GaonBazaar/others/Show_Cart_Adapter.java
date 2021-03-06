package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devrik.GaonBazaar.Model.ShowCartmodel;
import com.devrik.GaonBazaar.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_Cart_Adapter extends RecyclerView.Adapter<Show_Cart_Adapter.ViewHolder> {
    Context context;
    ArrayList<ShowCartmodel> cartModelArrayList;
    String User_id="",Productid="";

    public Show_Cart_Adapter(Context context, ArrayList<ShowCartmodel> cartModelArrayList) {
        this.context = context;
        this.cartModelArrayList = cartModelArrayList;
    }
    @Override
    public Show_Cart_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showcard,parent,false);
        Show_Cart_Adapter.ViewHolder viewHolder=new Show_Cart_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Show_Cart_Adapter.ViewHolder holder, int position) {

       User_id= SharedHelper.getKey(context,APPCONSTANT.id);

        ShowCartmodel  cartmodel = cartModelArrayList.get(position);
        //Picasso.get().load(cartmodel.getPath()+cartmodel.getProduct_image()).into(holder.img_seeds);
        if (!cartmodel.equals("")) {

            SharedHelper.putkey(context,APPCONSTANT.Product_ID,cartmodel.getProduct_id());

            holder.txt_name.setText(cartmodel.getProduct_name());
            holder.txt_quantity.setText(cartmodel.getQuantity());
            holder.txtprice.setText(cartmodel.getPrice());

            try {
                Log.e("dfgkjhjfgk",cartmodel.getPath()+"");
                Log.e("dfgkjhjfgk",cartmodel.getProduct_image()+"");
                Glide.with(context).load(cartmodel.getPath() + cartmodel.getProduct_image())
                       //.placeholder(R.drawable.crop).override(50, 50)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img_seeds);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dgfadf",e.getMessage());
            }

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img_seeds;
        TextView txt_name,txtprice,txt_quantity;
        MaterialButton btn_next;
        Spinner spinqty;
        ImageView delete;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_seeds = itemView.findViewById(R.id.img_seeds);
            txt_name= itemView.findViewById(R.id.txt_name);
            txtprice = itemView.findViewById(R.id.txtprice);
            txt_quantity= itemView.findViewById(R.id.txt_quantity);
            btn_next=itemView.findViewById(R.id.btn_next);
            spinqty=itemView.findViewById(R.id.spinqty);
            delete=itemView.findViewById(R.id.delete);
            progressBar=itemView.findViewById(R.id.progressBar);

        }
    }

    public void delete(){
         Productid = SharedHelper.getKey(context,APPCONSTANT.Product_ID);
         AndroidNetworking.post(API.delete_cart)
        .addBodyParameter("user_id",User_id)
        .addBodyParameter("id",Productid)
        .setPriority(Priority.HIGH)
        .setTag("cart_delet")
        .build()
        .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("fdkgu", response.toString());
                try {

                    if (response.getString("result").equals("successfully"))
                    {
                        Toast.makeText(context, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                        
                    }else {

                        Toast.makeText(context, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                        
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });


    }

}
