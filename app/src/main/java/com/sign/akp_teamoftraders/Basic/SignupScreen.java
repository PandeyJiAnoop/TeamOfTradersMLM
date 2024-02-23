package com.sign.akp_teamoftraders.Basic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sign.akp_teamoftraders.MyAccount.MyProfile;
import com.sign.akp_teamoftraders.R;
import com.sign.akp_teamoftraders.SlidingMenu.CustomerRegistrtaion;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByGet;
import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;


public class SignupScreen extends AppCompatActivity {
//    public class SignupScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] courses = { "Direction", "Left", "Right" };
    EditText sponser_et,name_et,email_et,mob_et,c_pass_et,pass_et,username_et,sponser_name_et;
    TextView signin_tv;
    AppCompatButton signup_btn;
    SearchableSpinner country_et;
    String stateid; ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        findId();
        OnClick();
        CountryList();

//        Spinner spin = findViewById(R.id.direction_sp);
//        spin.setOnItemSelectedListener(this);
//        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
//        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(ad);
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
                            pass_et.getText().toString(),sponser_et.getText().toString(),"",stateid,username_et.getText().toString());
                }
            }
        });
        signin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                startActivity(intent);
            }
        });

        sponser_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private void findId() {
        country_et=findViewById(R.id.country_et);
        sponser_et=findViewById(R.id.sponser_et);
        name_et=findViewById(R.id.name_et);
        email_et=findViewById(R.id.email_et);
        mob_et=findViewById(R.id.mob_et);
        pass_et=findViewById(R.id.pass_et);
        c_pass_et=findViewById(R.id.c_pass_et);
        signup_btn=findViewById(R.id.signup_btn);
        signin_tv=findViewById(R.id.signin_tv);
        username_et=findViewById(R.id.username_et);
        sponser_name_et=findViewById(R.id.sponser_name_et);
        country_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        if (country_et.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
                            stateid = StateId.get(j);
//                            stateid = StateId.get(j - 1);
                            break;
                        } } } }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
//    // Performing action when ItemSelected
//    // from spinner, Overriding onItemSelected method
//    @Override
//    public void onItemSelected(AdapterView arg0, View arg1, int position, long id)
//    {
//        Toast.makeText(getApplicationContext(),courses[position], Toast.LENGTH_LONG).show();
//    }
//    @Override
//    public void onNothingSelected(AdapterView arg0)
//    {
//        // Auto-generated method stub
//    }

    @Override
    public void onBackPressed() {
        finish();
    }




    public void  getRegAPI(String action, String Country, String Email,String FullName,String MobilNo,String Password,String SponsorId,String UserId,String phonecode,String WalletId){
        String otp1 = new GlobalAppApis().NewAccount(action,Country,Email,FullName,MobilNo,Password,SponsorId,UserId,phonecode,WalletId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getNewAccount(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resppp",result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                            builder.setTitle("Registration Successfully!")
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
                                        }
                                    });
                            builder.create().show();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                        builder.setTitle("ERROR!")
                                .setMessage(object.getString("Message"))
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
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
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
        }, SignupScreen.this, call1, "", true);
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
                        country_et.setAdapter(new ArrayAdapter<String>(SignupScreen.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SignupScreen.this, call1, "", true);
    }
    private void getDataFromSponser(String refid)
    {
        String otp1 = new GlobalAppApis().CheckSponserA(refid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getCheckSponser(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equals("false")){
                        sponser_et.setText("");
                        sponser_name_et.setText("");

                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                        builder.setTitle("Invalid")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();

                    }
                    else {
                        JSONArray Jarray = object.getJSONArray("Resp");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String Name = jsonobject.getString("Name");
                            sponser_name_et.setText(Name);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, SignupScreen.this, call1, "", true);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getDataFromSponser(sponser_et.getText().toString());
    }
}