package com.devrik.GaonBazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.Model.ShowCartmodel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.devrik.GaonBazaar.others.Show_Cart_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TotalAmountActivity extends AppCompatActivity {

    Context context = TotalAmountActivity.this;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ShowCartmodel> cartModelArrayList = new ArrayList<>();
    RecyclerView rv_showcard;
    ImageView back_btn;
    TextView total_price;
    Button btn_next;
    ProgressBar progressBar;
    String User_Id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_amount);
        rv_showcard = findViewById(R.id.rv_showcard);
        back_btn = findViewById(R.id.back_btn);
        total_price = findViewById(R.id.total_price);
        btn_next = findViewById(R.id.btn_next);
        progressBar = findViewById(R.id.progressBar);


        User_Id = SharedHelper.getKey(TotalAmountActivity.this, APPCONSTANT.id);
        Log.e("shgfk",User_Id);
        show_cart();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TotalAmountActivity.this,SeedDetailScreenActivity.class));
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TotalAmountActivity.this,AvailableBalanceActivity.class));
            }
        });
    }
    public void show_cart() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_cart)
                .addBodyParameter("user_id",User_Id)
                .setPriority(Priority.HIGH)
                .setTag("show cart")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            Log.e("dhkjhv",response.toString());
                            if (response.getString("result").equals("successfull"));
                            JSONArray jsonArray = new JSONArray(response.getString("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("jfnbgjc", response.getString("data") );
                                ShowCartmodel showcartmodel = new ShowCartmodel();
                                showcartmodel.setId(jsonObject.getString("id"));
                                showcartmodel.setProduct_id(jsonObject.getString("product_id"));
                                showcartmodel.setProduct_price(jsonObject.getString("product_price"));
                                showcartmodel.setTotal_price(jsonObject.getString("total_price"));
                                showcartmodel.setSubtotal_amount(jsonObject.getString("subtotal_amount"));

                                    JSONArray jsonArray1 =new JSONArray(jsonObject.getString("product"));
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                    Log.e("hsfhjgj",jsonObject.getString("product"));
                                    JSONObject object = jsonArray1.getJSONObject(j);
                                    showcartmodel.setProduct_id(object.getString("product_id"));
                                    showcartmodel.setPrice(object.getString("price"));
                                    showcartmodel.setProduct_name(object.getString("product_name"));
                                    showcartmodel.setQuantity(object.getString("quantity"));
                                    showcartmodel.setProduct_image(object.getString("product_image"));
                                    showcartmodel.setPath(object.getString("path"));
                                    total_price.setText(object.optString("total_price"));
                                    cartModelArrayList.add(showcartmodel);
                                }
                                rv_showcard.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(TotalAmountActivity.this, RecyclerView.VERTICAL, false);
                                rv_showcard.setLayoutManager(layoutManager);
                                Show_Cart_Adapter adapter = new Show_Cart_Adapter(context, cartModelArrayList);
                                rv_showcard.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("djkfg",e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("gdnjbg",anError.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
}