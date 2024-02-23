package com.sign.akp_teamoftraders.MyAccount;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.DashboardActivity;
import com.sign.akp_teamoftraders.R;

import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;


public class ChangePassword extends AppCompatActivity {
 ImageView menuImg;
 TextInputEditText edt_old_pass,edt_new_pass,edt_conf_pass;
 AppCompatButton btn_login;
 String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
       findId();

       onClick();
    }

    private void onClick() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_new_pass.getText().toString().equalsIgnoreCase("")){
                    edt_new_pass.setError("Fields can't be blank!");
                    edt_new_pass.requestFocus();
                }
                else if (edt_old_pass.getText().toString().equalsIgnoreCase("")){
                    edt_old_pass.setError("Fields can't be blank!");
                    edt_old_pass.requestFocus();
                }
                else if (edt_conf_pass.getText().toString().equalsIgnoreCase("")){
                    edt_conf_pass.setError("Fields can't be blank!");
                    edt_conf_pass.requestFocus();
                }
                else if (!edt_old_pass.getText().toString().equalsIgnoreCase(edt_conf_pass.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Old and New Password not Matched!",Toast.LENGTH_LONG).show();
                }

                else {
                    ChangepasswordAPI(edt_new_pass.getText().toString(),edt_old_pass.getText().toString(),UserId);
                }
            }
        });
    }

    private void findId() {
        edt_old_pass=findViewById(R.id.edt_old_pass);
        edt_new_pass=findViewById(R.id.edt_new_pass);
        edt_conf_pass=findViewById(R.id.edt_conf_pass);
        btn_login=findViewById(R.id.btn_login);
        menuImg=findViewById(R.id.menuImg);

    }

    public void  ChangepasswordAPI(String NewPassword,String OldPassword,String UserId){
        String otp1 = new GlobalAppApis().ChangePassword(NewPassword,OldPassword,UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getChangePassword(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resppp",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setTitle("Change Password Successfully!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
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
        }, ChangePassword.this, call1, "", true);
    }

}