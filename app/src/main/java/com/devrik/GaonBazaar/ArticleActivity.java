package com.devrik.GaonBazaar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.devrik.GaonBazaar.Model.ArticleModel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.ArticleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    ImageView back;
RecyclerView rv_article;
ProgressBar progressBar;

    ArrayList<ArticleModel> articleModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        back=findViewById(R.id.back);
        rv_article=findViewById(R.id.rv_article);
        progressBar=findViewById(R.id.progressBar);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Artical();
    }


    public void Artical() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_artical)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("safdadfa", response.toString());
                        articleModelArrayList= new ArrayList<>();
                        try {
                            for (int i = 0; i <response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.e("dfzcc",response.toString());
                                ArticleModel articleModel = new ArticleModel();

                                articleModel.setArtical(jsonObject.getString("artical"));
                                articleModel.setDate(jsonObject.getString("date"));
                                articleModel.setImage(jsonObject.getString("image"));
                                articleModel.setName(jsonObject.getString("name"));
                                articleModel.setPath(jsonObject.getString("path"));
                                articleModel.setId(jsonObject.getString("id"));
                                articleModelArrayList.add(articleModel);
                            }

                            rv_article.setHasFixedSize(true);
                            rv_article.setLayoutManager(new LinearLayoutManager(ArticleActivity.this, RecyclerView.VERTICAL, false));
                            rv_article.setAdapter(new ArticleAdapter(ArticleActivity.this,articleModelArrayList));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("dgfffdf", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("fhsdds", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}