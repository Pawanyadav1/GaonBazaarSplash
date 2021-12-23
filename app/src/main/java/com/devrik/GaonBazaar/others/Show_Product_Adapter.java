package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_Product_Adapter extends RecyclerView.Adapter<Show_Product_Adapter.ViewHolder> {
        Context context;
        ArrayList<MyModel> myModelArrayList;
    String USERID="";
    String Productid="";
    String Quantity="";

        public Show_Product_Adapter(Context context, ArrayList<MyModel> myModelArrayList) {
            this.context = context;
            this.myModelArrayList = myModelArrayList;

        }
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            MyModel  myModel = myModelArrayList.get(position);
            Picasso.get().load(myModel.getPath()+myModel.getImage()).into(holder.seed_img);
            USERID = SharedHelper.getKey(context,APPCONSTANT.id);


            if (!myModel.equals("")) {
                holder.txtCommdity.setText(myModel.getCommdity());
                holder.txtvariety.setText(myModel.getVariety());
                holder.txtbrand.setText(myModel.getBrandName());
                holder.txtcompeny.setText(myModel.getCompany());
                holder.name.setText(myModel.getName());
                holder.txtprice.setText(myModel.getPrice());

                try {
                    Log.e("fjk", myModel.getPath()+"");
                    Log.e("fjk", myModel.getImage()+"");

                    Glide.with(context).load(myModel.getPath()+myModel.getImage())
                            .placeholder(R.drawable.seeds).override(50, 50)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.seed_img);

                   holder.btn_addcard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.i("TAG","RAM"+myModel);
                         //   Toast.makeText(context,"found id"+ myModel.getId(),Toast.LENGTH_SHORT).show();
                            Productid = myModel.getId();
                            Quantity = myModel.getQuantity();
                            addcard(holder);
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
            return myModelArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            CircleImageView seed_img;
            Button btn_addcard,btn_edtcard,decrease,increase;
            TextView text_quantity;
            TextView name,txtCommdity,txtvariety,txtbrand,txtprice,txtcompeny;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                seed_img=itemView.findViewById(R.id.seed_img);
                txtCommdity=itemView.findViewById(R.id.txtCommdity);
                txtvariety=itemView.findViewById(R.id.txtvariety);
                text_quantity = itemView.findViewById(R.id.text_quantity);
                btn_addcard=itemView.findViewById(R.id.btn_addcard);
                btn_edtcard = itemView.findViewById(R.id.btn_edtcard);
                txtbrand=itemView.findViewById(R.id.txtbrand);
                txtcompeny=itemView.findViewById(R.id.txtcompeny);
                name=itemView.findViewById(R.id.name);
                decrease=itemView.findViewById(R.id.decrease);
                increase=itemView.findViewById(R.id.increase);
                txtprice=itemView.findViewById(R.id.txtprice);
            }
        }

    public void addcard(ViewHolder holder){

        AndroidNetworking.post(API.add_tocart)
                .addBodyParameter("user_id",USERID)
                .addBodyParameter("product_id",Productid)
                .addBodyParameter("quantity",Quantity)
                .setPriority(Priority.HIGH)
                .setTag("add card")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("dfzcadc",response.toString());
                            if (response.getString("result").equals("successfully")){
                                holder.btn_addcard.setVisibility(View.GONE);
                                holder.btn_edtcard.setVisibility(View.VISIBLE);
                               // holder.btn_addcard.setVisibility(View.VISIBLE);
                                Toast.makeText(context, ""+response.getString("result"), Toast.LENGTH_SHORT).show();

                            }else{

                                holder.btn_addcard.setVisibility(View.VISIBLE);
                                holder.btn_edtcard.setVisibility(View.GONE);
                               // holder.btn_edtcard.setVisibility(View.VISIBLE);
                                Toast.makeText(context, ""+response.getString("result"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("jhdgfg",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("fgdhj",anError.getMessage());

                    }
                });
    }

}

