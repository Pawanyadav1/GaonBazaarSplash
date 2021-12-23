package com.devrik.GaonBazaar.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.devrik.GaonBazaar.BuyItemActivity;
import com.devrik.GaonBazaar.HomeScreenActivity;
import com.devrik.GaonBazaar.R;
import com.devrik.GaonBazaar.SellItemActivity;
import com.google.android.material.card.MaterialCardView;

public class homefragment extends Fragment {
    TextView txt1;
    ImageView buy1,menu;
    MaterialCardView buyitems,sell_item;
    ProgressBar progressBar;
    // ViewFlipper flipper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

//  int imgarray[]={R.drawable.crop,R.drawable.spry,R.drawable.gaon,R.drawable.farmer,R.drawable.financial,R.drawable.futurepri};
        sell_item =view.findViewById(R.id.sell_item);
        buyitems = view.findViewById(R.id.buyitems);
        txt1 = view.findViewById(R.id.txt1);
        buy1 = view.findViewById(R.id.buy1);
        menu =view.findViewById(R.id.menu);
        progressBar =view.findViewById(R.id.progressBar);
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
}
