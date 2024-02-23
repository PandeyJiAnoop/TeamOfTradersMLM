package com.sign.akp_teamoftraders.MyAccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_teamoftraders.DashboardActivity;
import com.sign.akp_teamoftraders.R;
import com.sign.akp_teamoftraders.SlidingMenu.CustomerRegistrtaion;
import com.sign.akp_teamoftraders.SlidingMenu.RaiseAddRequest;
import com.sign.akp_teamoftraders.SlidingMenu.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextInputEditText sponser_id_et, name_et, mobile_et, email_et, gender_et, usdt_adress_et,
            BranchName_et, IFSCCode_et, AccountHolderName_et, AccountNumber_et, UPIId_et, nom_name_et, nom_rel_et, sponser_name_et;
    AppCompatButton btn_submit;
    SearchableSpinner country_et;
    String stateid;
    ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();
    TextView select_tv1, select_tv, status_text;
    ImageView img_showProfile, img_showProfile1;
    String[] courses = {"Saving", "Current"};
    Spinner spin;
    String SelectedValue;
    boolean isPan, isAdhar;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String storagePermission[];
    private static final int STORAGE_REQUEST_CODE = 400;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    ImageView menuImg;
    String UserId,temp = "", temp1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Findid();
        OnClick();
        CountryList();
        ProfileAPI("", UserId);
    }

    private void OnClick() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usdt_adress_et.getText().toString().equalsIgnoreCase("")) {
                    usdt_adress_et.setError("Fields can't be blank!");
                    usdt_adress_et.requestFocus();
                } else {
                    UpdateProfileAPI("", "", email_et.getText().toString(), name_et.getText().toString(), mobile_et.getText().toString(),
                            "", "", sponser_id_et.getText().toString(), usdt_adress_et.getText().toString(), UserId);
                }
            }
        });
        country_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        if (country_et.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
//                            stateid = StateId.get(j);
                            stateid = StateId.get(j - 1);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPan = true;
                isAdhar = false;
                if(checkAndRequestPermissions(MyProfile.this)){
                    selectImage();
                }}
        });
        select_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPan = false;
                isAdhar = true;
                if(checkAndRequestPermissions(MyProfile.this)){
                    selectImage();
                }
            }
        });
        spin = findViewById(R.id.rupee_sp);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);

    }

    private void Findid() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        checkAndRequestPermissions(this);
        menuImg = findViewById(R.id.menuImg);
        sponser_id_et = findViewById(R.id.sponser_id_et);
        sponser_name_et = findViewById(R.id.sponser_name_et);
        name_et = findViewById(R.id.name_et);
        mobile_et = findViewById(R.id.mobile_et);
        email_et = findViewById(R.id.email_et);
        gender_et = findViewById(R.id.gender_et);
        usdt_adress_et = findViewById(R.id.usdt_adress_et);

        BranchName_et = findViewById(R.id.BranchName_et);
        IFSCCode_et = findViewById(R.id.IFSCCode_et);
        AccountHolderName_et = findViewById(R.id.AccountHolderName_et);
        AccountNumber_et = findViewById(R.id.AccountNumber_et);
        UPIId_et = findViewById(R.id.UPIId_et);
        nom_name_et = findViewById(R.id.nom_name_et);
        nom_rel_et = findViewById(R.id.nom_rel_et);

        country_et = findViewById(R.id.country_et);
        btn_submit = findViewById(R.id.btn_submit);
        select_tv = findViewById(R.id.select_tv);
        img_showProfile = findViewById(R.id.img_showProfile);

        select_tv1 = findViewById(R.id.select_tv1);
        img_showProfile1 = findViewById(R.id.img_showProfile1);

        status_text = findViewById(R.id.status_text);
    }


    public void ProfileAPI(String action, String userid) {
        String otp1 = new GlobalAppApis().Profile(action, userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getProfile(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String bnbAddress = jsonobject.getString("BNBAddress");
                        String btcAddress = jsonobject.getString("BTCAddress");
                        String customerId = jsonobject.getString("CustomerId");
                        String ethAddress = jsonobject.getString("ETHAddress");
                        String emailId = jsonobject.getString("EmailId");
                        String gender = jsonobject.getString("Gender");
                        String mobileNo = jsonobject.getString("MobileNo");
                        String name = jsonobject.getString("Name");
                        String sponsorId = jsonobject.getString("SponsorId");
                        String title = jsonobject.getString("Title");
                        String usdtAddress = jsonobject.getString("USDTAddress");

                        sponser_id_et.setText(sponsorId);
                        sponser_name_et.setText(sponsorId);
                        name_et.setText(name);
                        mobile_et.setText(mobileNo);
                        email_et.setText(emailId);
                        gender_et.setText(gender);
                        usdt_adress_et.setText(usdtAddress);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, MyProfile.this, call1, "", true);
    }

    public void UpdateProfileAPI(String action, String Country, String Email, String FullName, String MobilNo, String NewPassword,
                                 String OldPassword, String SponsorId, String USDTAddress, String UserId) {
        String otp1 = new GlobalAppApis().UpdateProfile(action, Country, Email, FullName, MobilNo, NewPassword, OldPassword, SponsorId, USDTAddress, UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getUpdateAccountProfile(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resppp", result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")) {
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                        builder.setTitle("Update Successfully!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                        builder.setTitle("ERROR!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                    }
                                });
                        builder.create().show();
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                    builder.setTitle("ERROR!")
                            .setMessage("Something Went Wrong!!")
                            .setCancelable(false)
                            .setIcon(R.drawable.appicon)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                            });
                    builder.create().show();
                    e.printStackTrace();
                }
            }
        }, MyProfile.this, call1, "", true);
    }

    public void CountryList() {
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_GetCountryList();
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_GetCountryList", "cxc" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status") == false) {
                        String msg = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = object.getJSONArray("MyDirectResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("CountryCode"));
                            String statename = jsonObject1.getString("CountryName");
                            StateName.add(statename);
                        }
                        country_et.setAdapter(new ArrayAdapter<String>(MyProfile.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, MyProfile.this, call1, "", true);
    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method
    @Override
    public void onItemSelected(AdapterView arg0, View arg1, int position, long id)
    {
        SelectedValue=spin.getSelectedItem().toString();
//        Toast.makeText(getApplicationContext(),spin.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView arg0)
    {
        // Auto-generated method stub
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel" };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(MyProfile.this);
                if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        //permission allowed, take picture
                            galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (isPan == true){
            Toast.makeText(getApplicationContext(),""+bm,Toast.LENGTH_LONG).show();
            img_showProfile.setImageBitmap(bm);
            Bitmap immagex=bm;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b,Base64.DEFAULT);
        }
        else if (isAdhar == true){
            Toast.makeText(getApplicationContext(),""+bm,Toast.LENGTH_LONG).show();
            img_showProfile1.setImageBitmap(bm);
            Bitmap immagex=bm;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] b = baos.toByteArray();
            temp1 = Base64.encodeToString(b,Base64.DEFAULT);
        }
//
    }
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}