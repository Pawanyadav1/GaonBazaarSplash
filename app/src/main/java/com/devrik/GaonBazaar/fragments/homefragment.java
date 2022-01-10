package com.devrik.GaonBazaar.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.devrik.GaonBazaar.ArticleActivity;
import com.devrik.GaonBazaar.BuyItemActivity;
import com.devrik.GaonBazaar.HomeScreenActivity;
import com.devrik.GaonBazaar.Model.ArticleModel;
import com.devrik.GaonBazaar.Model.MandiRateModel;
import com.devrik.GaonBazaar.Model.SliderImageData;
import com.devrik.GaonBazaar.R;
import com.devrik.GaonBazaar.SellItemActivity;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.ArticleAdapter;
import com.devrik.GaonBazaar.others.MandiRateAdapter;
import com.devrik.GaonBazaar.others.SliderAdapterExample;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homefragment extends Fragment {
    TextView txt1,text_more;
    ImageView buy1,menu;
    TextView buyitems,sell_item;
    ProgressBar progressBar;
    TextView marqueeText;
    SliderView imageSlider;

    RecyclerView rv_mandirate,rv_article;
    // ViewFlipper flipper;

    SliderAdapterExample sliderAdapter;
    ArrayList<SliderImageData> sliderImageDataArrayList;
    ArrayList<MandiRateModel> mandiRateModelArrayList;
    ArrayList<ArticleModel> articleModelArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

// int imgarray[]={R.drawable.crop,R.drawable.spry,R.drawable.gaon,R.drawable.farmer,R.drawable.financial,R.drawable.futurepri};
        sell_item =view.findViewById(R.id.sell_item);
        buyitems = view.findViewById(R.id.buyitems);
        txt1 = view.findViewById(R.id.txt1);
        buy1 = view.findViewById(R.id.buy1);
        buy1 = view.findViewById(R.id.buy1);
        rv_mandirate = view.findViewById(R.id.rv_mandirate);
        rv_article = view.findViewById(R.id.rv_article);
        text_more =view.findViewById(R.id.text_more);
        menu=view.findViewById(R.id.menu);

        imageSlider = view.findViewById(R.id.imageSlider);


        progressBar =view.findViewById(R.id.progressBar);

        showSlider();


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreenActivity.drawer.openDrawer(GravityCompat.START);
            }
        });


        sell_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SellItemActivity.class);
                startActivity(intent);
            }
        });

        buyitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuyItemActivity.class);
                startActivity(intent);
            }
        });

        text_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ArticleActivity.class));
            }
        });

        MandiRate();
        Artical();
        onBack(view);
        return view;
}

    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.popup_home_fragment);
                    dialog.setCancelable(true);
                    Button btn_yes = dialog.findViewById(R.id.btn_yes);
                    Button btn_no = dialog.findViewById(R.id.btn_no);

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finishAffinity();
                        }
                    });

                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
                return true;

            }
        });

    }


    //showSlider api

    public void showSlider() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_slider)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Log.e("fdghfdhfdhfdh",response.toString());
                        progressBar.setVisibility(View.GONE);
                        try {
                            sliderImageDataArrayList = new ArrayList<>();
                            sliderAdapter = new SliderAdapterExample(sliderImageDataArrayList, getActivity());
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                SliderImageData sliderImageData = new SliderImageData();
                                sliderImageData.setImage(jsonObject.getString("image"));
                                sliderImageData.setPath(jsonObject.getString("path"));
                                sliderImageDataArrayList.add(sliderImageData);

                            }

                            imageSlider.setSliderAdapter(sliderAdapter);
                            imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            imageSlider.setIndicatorSelectedColor(Color.GREEN);
                            imageSlider.setIndicatorUnselectedColor(Color.LTGRAY);
                            imageSlider.setScrollTimeInSec(3);
                            imageSlider.startAutoCycle();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    public void MandiRate() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_rates)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("safdadfa", response.toString());
                        mandiRateModelArrayList= new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.e("dfzcc",response.toString());
                                MandiRateModel mandiRateModel = new MandiRateModel();

                                mandiRateModel.setCrops_name(jsonObject.getString("crops_name"));
                                mandiRateModel.setCrops_price(jsonObject.getString("crops_price"));
                                mandiRateModel.setId(jsonObject.getString("id"));
                                mandiRateModelArrayList.add(mandiRateModel);
                            }

                            rv_mandirate.setHasFixedSize(true);
                            rv_mandirate.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                            rv_mandirate.setAdapter(new MandiRateAdapter(getActivity(),mandiRateModelArrayList));

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
                            for (int i = 0; i <1; i++) {

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
                            rv_article.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                            rv_article.setAdapter(new ArticleAdapter(getContext(),articleModelArrayList));

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