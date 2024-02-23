package com.sign.akp_teamoftraders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;

import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class Tickets extends AppCompatActivity {
 ImageView menuImg;
 String UserId;
AppCompatButton btn_submit;
TextInputEditText et_sub,et_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
         SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
         UserId= sharedPreferences.getString("U_id", "");
          FindId();
         OnClick();

    }

    private void OnClick() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_sub.getText().toString().equals("")){
                    et_sub.setError("Field can't be blank!");
                    et_sub.requestFocus();
                }

               else if (et_msg.getText().toString().equals("")){
                    et_msg.setError("Field can't be blank!");
                    et_msg.requestFocus();
                }
               else {
                   SaveAPI(et_msg.getText().toString(),et_sub.getText().toString(),UserId);
                }
            }
        });
    }

    private void FindId() {
        menuImg=findViewById(R.id.menuImg);
        et_sub=findViewById(R.id.et_sub);
        et_msg=findViewById(R.id.et_msg);

        btn_submit=findViewById(R.id.btn_submit);

    }


    public void SaveAPI(String Message,String Subject,String UserId) {
        String otp = new GlobalAppApis().Support(Message,Subject,UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getSupport(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tickets.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }});
                        builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Tickets.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }});
                        builder.create().show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, Tickets.this, call, "", true);
    }

}