package com.devrik.GaonBazaar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.Model.AddStateModel;
import com.devrik.GaonBazaar.Model.Addblockmodel;
import com.devrik.GaonBazaar.Model.Adddistrictmodel;
import com.devrik.GaonBazaar.Model.Addvillagemodel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class UpDateProfileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String ID = "";
    MaterialButton button2;
    EditText name, fname, mobile;
    ImageView camera,addidimage,adharphoto;
    CircleImageView profile_photo;
    Spinner spindistrict,spinstate,spinblock,spinvillage;
    Spinner spin_id;
    TextView t;

    ProgressBar progressBar;
    String  selectedItemID ="", tutorialsName="";
    String selected;

    ArrayList<AddStateModel> addStateModelArrayList;
    ArrayList<String> Arr_StateID;
    ArrayList<String> Arr_stateName;

    ArrayList<Adddistrictmodel> adddistrictModelArrayList;
    ArrayList<String> Arr_districtID = new ArrayList<>();
    ArrayList<String> Arr_districtName = new ArrayList<>();

    ArrayList<Addblockmodel>addblockModelArrayList;
    ArrayList<String> Arr_blockID = new ArrayList<>();
    ArrayList<String> Arr_blockName = new ArrayList<>();

    ArrayList<Addvillagemodel>addvillageModelArrayList;
    ArrayList<String> Arr_villageID = new ArrayList<>();
    ArrayList<String> Arr_villageName = new ArrayList<>();

    private static final String IMAGE_DIRECTORY = "/directory";
    File f,f2;
    private int GALLERY = 1, CAMERA = 2;
    private int GALLERY1 = 3, CAMERA1 = 4;

    String stateID="",strdistricID="",strBlockID="",strVillageId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date_profile);

        camera = findViewById(R.id.camera);
        name = findViewById(R.id.name);
        fname = findViewById(R.id.fname);
        progressBar = findViewById(R.id.progressBar);
        profile_photo = findViewById(R.id.profile_photo);
        adharphoto = findViewById(R.id.adharphoto);
        mobile = findViewById(R.id.mobile);
        spinstate = findViewById(R.id.spinstate);
        spindistrict = findViewById(R.id.spindistrict);
        spinblock = findViewById(R.id.spinblock);
        spinvillage = findViewById(R.id.spinvillage);
        addidimage = findViewById(R.id.addidimage);
        button2 = findViewById(R.id.button2);
        spin_id = findViewById(R.id.spin_id);

        ID = SharedHelper.getKey(UpDateProfileActivity.this,APPCONSTANT.id);

        showDetails();

        Arr_StateID = new ArrayList<>();
        Arr_stateName = new ArrayList<>();
        Arr_StateID.add("0");
        Arr_stateName.add("Select");

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Arr_stateName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinstate.setAdapter(adapter);

        Arr_districtID = new ArrayList<>();
        Arr_districtName = new ArrayList<>();
        Arr_districtID.add("0");
        Arr_districtName.add("Select");

        ArrayAdapter adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Arr_districtName);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spindistrict.setAdapter(adapter1);

        Arr_blockID = new ArrayList<>();
        Arr_blockName = new ArrayList<>();
        Arr_blockID.add("0");
        Arr_blockName.add("Select");

        ArrayAdapter adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, Arr_blockName);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinblock.setAdapter(adapter2);

        Arr_villageID = new ArrayList<>();
        Arr_villageName = new ArrayList<>();
        Arr_villageID.add("0");
        Arr_villageName.add("Select");

        ArrayAdapter adapter3 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, Arr_villageName);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinvillage.setAdapter(adapter3);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Type of ID");
        arrayList.add("Aadhaar card");
        arrayList.add("PAN card");
        arrayList.add("Voter id card");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UpDateProfileActivity.this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_id.setAdapter(arrayAdapter);

        spin_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tutorialsName = parent.getItemAtPosition(position).toString();
                if (tutorialsName.equals("Select Type of ID")){
                    selectedItemID="";

                }else {

                    if (tutorialsName.equals("Aadhaar card")){
                        selectedItemID="1";

                    }else if (tutorialsName.equals("PAN card")){
                        selectedItemID="2";

                    }else if (tutorialsName.equals("Voter id card")){
                        selectedItemID="3";

                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }

        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                typeselectId();

               updateprofile();
            }

        });

        Show_State();

        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();
            }
        });

        addidimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialogId();

            }
        });
    }
    private void typeselectId() {

        if (selectedItemID.equals("1")){
            SharedHelper.putkey(UpDateProfileActivity.this,APPCONSTANT.USER_ID_CARD,tutorialsName);

        } else if(selectedItemID.equals("2")){
            SharedHelper.putkey(UpDateProfileActivity.this,APPCONSTANT.USER_ID_CARD,tutorialsName);

        } else if(selectedItemID.equals("3")){
            SharedHelper.putkey(UpDateProfileActivity.this,APPCONSTANT.USER_ID_CARD,tutorialsName);

        } else {
            Toast.makeText(UpDateProfileActivity.this, "Please select", Toast.LENGTH_SHORT).show();
        }
    }
    public void showPictureDialog() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(UpDateProfileActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery();
                        break;

                    case 1:
                        captureFromCamera();
                        break;
                }

            }
        });

        builder.show();
    }

    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }

    public void captureFromCamera() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }

    public void showPictureDialogId() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(UpDateProfileActivity.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallerys();
                        break;

                    case 1:
                        captureFromCameras();
                        break;
                }

            }
        });

        builder.show();
    }

    public void choosePhotoFromGallerys() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY1);
    }

    public void captureFromCameras() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            profile_photo.setVisibility(View.VISIBLE);
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String strimage = getRealPathFromURI(contentURI);
                    f2= new File(strimage);
                    profile_photo.setImageBitmap(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                    Toast.makeText(UpDateProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            profile_photo.setVisibility(View.VISIBLE);
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile_photo.setImageBitmap(thumbnail);
            Uri getImageUrei=getImageUri(UpDateProfileActivity.this,thumbnail);
            String strimage = getRealPathFromURI(getImageUrei);
            f2= new File(strimage);
            Toast.makeText(UpDateProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == GALLERY1) {
            adharphoto.setVisibility(View.VISIBLE);
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String strimage = getRealPathFromURI(contentURI);
                    f= new File(strimage);
                    adharphoto.setImageBitmap(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                    Toast.makeText(UpDateProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }
            }

        }else if (requestCode == CAMERA1) {
            adharphoto.setVisibility(View.VISIBLE);
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            adharphoto.setImageBitmap(thumbnail);

            Uri getImageUrei=getImageUri(UpDateProfileActivity.this,thumbnail);
            String strimage = getRealPathFromURI(getImageUrei);
            f= new File(strimage);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }


    private void updateprofile() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("sddssds", f +"");
        Log.e("sddssds", f2 +"");
        AndroidNetworking.initialize(UpDateProfileActivity.this);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();
        AndroidNetworking.initialize(UpDateProfileActivity.this, okHttpClient);
        AndroidNetworking.upload(API.update_profile)
                .addMultipartParameter("user_id",ID)
                .addMultipartParameter("name", name.getText().toString().trim())
                .addMultipartParameter("parent_name", fname.getText().toString().trim())
                .addMultipartParameter("phone", mobile.getText().toString().trim())
                .addMultipartParameter("state", stateID)
                .addMultipartParameter("district", strdistricID)
                .addMultipartParameter("block", strBlockID)
                .addMultipartParameter("village",strVillageId)
                .addMultipartParameter("typeof_id",selectedItemID)
                .addMultipartFile("photo",f)
                .addMultipartFile("image",f2)
                .setTag("update profile")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("sdjnv",response.toString());
                        try {
                            if (response.getString("result").equals("successfully")) {
                                Toast.makeText(UpDateProfileActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpDateProfileActivity.this, AddAccountDetailsScreen.class));

                            } else {
                                Toast.makeText(UpDateProfileActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("success", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("ffhghj", anError.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }


    private void showDetails() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("dfsgdgdf", ID);
        AndroidNetworking.post(API.update_profile)
                .addBodyParameter("user_id",ID)
                .setPriority(Priority.HIGH)
                .setTag("details")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("fsafdadfa", response.toString());
                        try {

                            name.setText(response.getString("name"));
                            mobile.setText(response.getString("phone"));
                            fname.setText(response.getString("parent_name"));
                            selected = spinstate.getSelectedItem().toString();
                            System.out.println(selected);
                            t.setText(selected);
                            spinstate.setSelection(0);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("dsfgdvd", e.getMessage() );
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("dsfgdxvd", anError.getMessage() );
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
    public void Show_State() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_state)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        addStateModelArrayList= new ArrayList<>();
                        Log.e("uygyggv", "onRespons " + response.toString());


                        try {
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject jsonObject = response.getJSONObject(i);
                                Arr_StateID.add(jsonObject.getString("id"));
                                Arr_stateName.add(jsonObject.getString("name"));


                            }

                            spinstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String stateId = Arr_StateID.get(position);
                                    Log.e("dcdcddcdc", stateId );

                                    ((TextView) spinstate.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spinstate.getChildAt(0)).setTextSize(15);

                                    if (stateId.equals("0")){

                                    }else {

                                        stateID=stateId;
                                    }

                                    show_district(stateId);
                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {
                                }
                            });


                        } catch (Exception exception) {
                            Log.e("abcdhd", exception.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("abcdhd", "onError: " + anError);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    public void show_district(String stateId) {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_district)
                .addBodyParameter("state_id",stateId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        adddistrictModelArrayList = new ArrayList<>();
                        Log.e("uygyhggv", "onRespo " + response.toString());


                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);
                                Arr_districtID.add(jsonObject.getString("id"));
                                Arr_districtName.add(jsonObject.getString("name"));

                            }

                            spindistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String districtId = Arr_districtID.get(position);
                                    Log.e("dcdcdddcdc", districtId );

                                    ((TextView) spindistrict.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spindistrict.getChildAt(0)).setTextSize(15);

                                    if (districtId.equals("0")){

                                    }else {
                                        strdistricID=districtId;
                                    }
                                    show_block(districtId);
                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {

                                }
                            });

                        } catch (Exception exception) {
                            Log.e("abcdhd", exception.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("abcdhd", "onError: " + anError);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }




    public void show_block(String districtId) {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_block)
                .addBodyParameter("district_id",districtId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        addblockModelArrayList = new ArrayList<>();
                     Log.e("uygyghjgv", "onRespo " + response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Arr_blockID.add(jsonObject.getString("id"));
                                Arr_blockName.add(jsonObject.getString("name"));
                            }

                            spinblock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String blockId = Arr_blockID.get(position);
                                    Log.e("dcdddcdc", blockId );

                                    ((TextView) spinblock.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spinblock.getChildAt(0)).setTextSize(15);
                                    if(blockId.equals("0")){

                                    }else{
                                        strBlockID=blockId;
                                    }
                                    show_village(blockId);
                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {
                                }
                            });

                        } catch (Exception exception) {
                            Log.e("abchd", exception.getMessage());
                            progressBar.setVisibility(View.GONE);


                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("abcdfrdhd", "onError: " + anError);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    public void show_village(String blockId) {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_village)
                .addBodyParameter("block_id",blockId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        addvillageModelArrayList = new ArrayList<>();
                        Log.e("uygygfggv", "onRespo " + response.toString());


                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Arr_villageID.add(jsonObject.getString("id"));
                                Arr_villageName.add(jsonObject.getString("name"));

                            }
                            spinvillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String villageId = Arr_villageID.get(position);
                                    Log.e("dcdddcdc", villageId );

                                    ((TextView) spinvillage.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spinvillage.getChildAt(0)).setTextSize(15);

                                    if(villageId.equals("0")) {

                                    }else{
                                        strVillageId=villageId;
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {


                                }
                            });

                        } catch (Exception exception) {
                            Log.e("abcd", exception.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("abchdgfhd", "onError: " + anError);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}