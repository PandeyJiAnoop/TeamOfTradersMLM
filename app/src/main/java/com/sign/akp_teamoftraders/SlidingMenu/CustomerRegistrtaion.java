package com.sign.akp_teamoftraders.SlidingMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.LoginScreen;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.Basic.SignupScreen;
import com.sign.akp_teamoftraders.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByGet;
import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class CustomerRegistrtaion extends AppCompatActivity {
    ImageView menuImg;
    EditText sponser_et,name_et,email_et,mob_et,c_pass_et,pass_et,username_et;
    AppCompatButton signup_btn;
    String UserId;
    SearchableSpinner country_et;
    String stateid; ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registrtaion);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        findId();
        OnClick();
        CountryList();

    }

    private void OnClick() {
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sponser_et.getText().toString().equalsIgnoreCase("")){
                    sponser_et.setError("Fields can't be blank!");
                    sponser_et.requestFocus();
                }
                else if (username_et.getText().toString().equalsIgnoreCase("")){
                    username_et.setError("Fields can't be blank!");
                    username_et.requestFocus();
                }
                else if (name_et.getText().toString().equalsIgnoreCase("")){
                    name_et.setError("Fields can't be blank!");
                    name_et.requestFocus();
                }
                else if (email_et.getText().toString().equalsIgnoreCase("")){
                    email_et.setError("Fields can't be blank!");
                    email_et.requestFocus();
                }
                else if (mob_et.getText().toString().equalsIgnoreCase("")){
                    mob_et.setError("Fields can't be blank!");
                    mob_et.requestFocus();
                }
                else if (pass_et.getText().toString().equalsIgnoreCase("")){
                    pass_et.setError("Fields can't be blank!");
                    pass_et.requestFocus();
                }
                else if (c_pass_et.getText().toString().equalsIgnoreCase("")){
                    c_pass_et.setError("Fields can't be blank!");
                    c_pass_et.requestFocus();
                }
                else if (!pass_et.getText().toString().equalsIgnoreCase(c_pass_et.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Password Not Matched!",Toast.LENGTH_LONG).show();

                }
                else {
                    getRegAPI("1",stateid,email_et.getText().toString(),name_et.getText().toString(),mob_et.getText().toString(),
                            pass_et.getText().toString(),sponser_et.getText().toString(),UserId,stateid,username_et.getText().toString());
                }
            }
        });


        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findId() {
        menuImg=findViewById(R.id.menuImg);
        country_et=findViewById(R.id.country_et);
        sponser_et=findViewById(R.id.sponser_et);
        name_et=findViewById(R.id.name_et);
        email_et=findViewById(R.id.email_et);
        mob_et=findViewById(R.id.mob_et);
        pass_et=findViewById(R.id.pass_et);
        c_pass_et=findViewById(R.id.c_pass_et);
        signup_btn=findViewById(R.id.signup_btn);
        username_et=findViewById(R.id.username_et);
        sponser_et.setText(UserId);

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
                        } } } }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void  getRegAPI(String action, String Country, String Email,String FullName,String MobilNo,String Password,String SponsorId,String UserId,String phonecode,String WalletId){
        String otp1 = new GlobalAppApis().NewAccount(action,Country,Email,FullName,MobilNo,Password,SponsorId,UserId,phonecode,WalletId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getNewAccount(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resppp",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                        builder.setTitle("Registration Successfully!")
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
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                        builder.setTitle("ERROR!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                    } });
                        builder.create().show();
                    }
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                    builder.setTitle("ERROR!")
                            .setMessage("Something Went Wrong!!")
                            .setCancelable(false)
                            .setIcon(R.drawable.appicon)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                    e.printStackTrace();
                }
            }
        }, CustomerRegistrtaion.this, call1, "", true);
    }
    public void CountryList() {
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_GetCountryList();
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_GetCountryList","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")==false){
                        String msg       = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONArray jsonArray = object.getJSONArray("MyDirectResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("CountryCode"));
                            String statename = jsonObject1.getString("CountryName");
                            StateName.add(statename);
                        }
                        country_et.setAdapter(new ArrayAdapter<String>(CustomerRegistrtaion.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, CustomerRegistrtaion.this, call1, "", true);
    }

}