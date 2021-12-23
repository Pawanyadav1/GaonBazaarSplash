package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class SelectTypeActivity extends AppCompatActivity {
MaterialButton button;
ProgressBar progressBar;
String tutorialsName="",selectedItem="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        Spinner spinner = findViewById(R.id.spin);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Type");
        arrayList.add("Farmer");
        arrayList.add("Vlv");
        arrayList.add("User");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SelectTypeActivity.this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tutorialsName = parent.getItemAtPosition(position).toString();
                if (tutorialsName.equals("Select Type")){
                    selectedItem="";

                }else {

                    if (tutorialsName.equals("Farmer")){
                        selectedItem="1";

                    }else if (tutorialsName.equals("Vlv")){
                        selectedItem="2";

                    }else if (tutorialsName.equals("User")){
                        selectedItem="3";
                    }

                }
                //Toast.makeText(parent.getContext(), "Selected :" + selectedItem, Toast.LENGTH_LONG).show();
            }
            @Override

            public void onNothingSelected(AdapterView <?> parent) {

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                typeselect();

            }
        });
    }

     private void typeselect() {
         progressBar.setVisibility(View.VISIBLE);

             if (selectedItem.equals("1")){
                 Intent intent = new Intent(SelectTypeActivity.this, SignInGaonActivity.class);
                 startActivity(intent);
                 progressBar.setVisibility(View.GONE);
                 SharedHelper.putkey(SelectTypeActivity.this,APPCONSTANT.USERTYPE,"1");
             } else if(selectedItem.equals("2")){
                 Intent intent = new Intent(SelectTypeActivity.this, SignInGaonActivity.class);
                 startActivity(intent);
                 progressBar.setVisibility(View.GONE);
                 SharedHelper.putkey(SelectTypeActivity.this,APPCONSTANT.USERTYPE,"2");
             } else if(selectedItem.equals("3")){
                 Intent intent = new Intent(SelectTypeActivity.this, SignInGaonActivity.class);
                 startActivity(intent);
                 progressBar.setVisibility(View.GONE);
                 SharedHelper.putkey(SelectTypeActivity.this,APPCONSTANT.USERTYPE,"3");
             } else {
                 Toast.makeText(SelectTypeActivity.this, "Please select type", Toast.LENGTH_SHORT).show();
             }
    }
}