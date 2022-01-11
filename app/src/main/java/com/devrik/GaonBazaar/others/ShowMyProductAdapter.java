package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.devrik.GaonBazaar.Model.MyModel;
import com.devrik.GaonBazaar.R;
import com.devrik.GaonBazaar.SeedDetailScreenActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowMyProductAdapter extends RecyclerView.Adapter<ShowMyProductAdapter.ViewHolder> {
    Context context;
    ArrayList<MyModel> myModelArrayList;
    int countBird, countBird1;

    String User_id="",Productid="";

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

        User_id= SharedHelper.getKey(context,APPCONSTANT.id);
        MyModel  myModel = myModelArrayList.get(position);
        Picasso.get().load(myModel.getPath()+myModel.getImage()).into(holder.seed_img);
        if (!myModel.equals("")) {
            holder.txtCommdity.setText(myModel.getCommdity());
            holder.txtvariety.setText(myModel.getVariety());
            holder.txtbrand.setText(myModel.getBrandName());
            holder.txtcompeny.setText(myModel.getCompany());
            holder.txtprice.setText(myModel.getPrice());
            holder.name.setText(myModel.getName());
            holder.edtBirdFromValue.setText(myModel.getQuantity());


            try {
                Log.e("sfdh", myModel.getPath()+"");
                Log.e("sfdh", myModel.getImage()+"");
                Glide.with(context).load(myModel.getPath()+myModel.getImage())
                        .placeholder(R.drawable.seeds).override(50, 50)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.seed_img);

                holder.txtBirdFromMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   String strProdQ = holder.edtBirdFromValue.getText().toString();

                        countBird--;

                        holder.edtBirdFromValue.setText(String.valueOf(countBird));
                        if (countBird > 1) {


                        }

               /* if (Integer.parseInt(strProdQ) > 1) {
                    String new_str = String.valueOf(Integer.parseInt(strProdQ) - 1);
                    Viewholder.edtBirdFromValue.setText(new_str);
                    //edtBirdFromValue.setText(new_str.substring(2,new_str.length()-2));
                }*/
                    }
                });

                holder.txtBirdFromPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        countBird1++;

                        holder.edtBirdFromValue.setText(String.valueOf(countBird1));
                    }
                });

                holder.btn_sell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SeedDetailScreenActivity.class);
                        context.startActivity(intent);
                    }
                });
                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete();
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
        Button btn_sell,btn_delete;
        EditText edtBirdFromValue;
        TextView name,txtCommdity,txtvariety,txtbrand,txtcompeny,txtprice,txtBirdFromMinus,txtBirdFromPlus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seed_img=itemView.findViewById(R.id.seed_img);
            txtCommdity=itemView.findViewById(R.id.txtCommdity);
            txtvariety=itemView.findViewById(R.id.txtvariety);
            txtbrand=itemView.findViewById(R.id.txtbrand);
            txtcompeny=itemView.findViewById(R.id.txtcompeny);
            name=itemView.findViewById(R.id.name);
            edtBirdFromValue=itemView.findViewById(R.id.edtBirdFromValue);
            txtBirdFromMinus=itemView.findViewById(R.id.txtBirdFromMinus);
            txtBirdFromPlus=itemView.findViewById(R.id.txtBirdFromPlus);
            txtprice=itemView.findViewById(R.id.txtprice);
            btn_sell=itemView.findViewById(R.id.btn_sell);
            btn_delete=itemView.findViewById(R.id.btn_delete);

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


