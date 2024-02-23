package com.sign.akp_teamoftraders.ActivationArea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.NetworkConnectionHelper;
import com.sign.akp_teamoftraders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class TransferHistory extends AppCompatActivity {
    ImageView menuImg;
    String UserId;
    SwipeRefreshLayout srl_refresh;
    RecyclerView chat_recyclerView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);
        FindId();
        OnClick();


         TopupDetails("",UserId);
    }

    private void OnClick() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline( TransferHistory.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText( TransferHistory.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        srl_refresh = findViewById(R.id.srl_refresh);
        chat_recyclerView = findViewById(R.id.chat_recyclerView);
        menuImg=findViewById(R.id.menuImg);
    }


    public void  TopupDetails(String action,String userid){
        String otp1 = new GlobalAppApis().TransferHistory(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getTransferHistory(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("TransferHistoryResp");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonObject1 = Jarray.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("Amount", jsonObject1.getString("Amount"));
                        hm.put("Date", jsonObject1.getString("Date"));
                        hm.put("Remark", jsonObject1.getString("Remark"));
                        hm.put("Type", jsonObject1.getString("Type"));
                        arrayList.add(hm);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager( TransferHistory.this, 1);
                     TransferAdapter walletHistoryAdapter = new  TransferAdapter( TransferHistory.this, arrayList);
                    chat_recyclerView.setLayoutManager(gridLayoutManager);
                    chat_recyclerView.setAdapter(walletHistoryAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        },  TransferHistory.this, call1, "", true);
    }


    public class TransferAdapter extends RecyclerView.Adapter< TransferAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public TransferAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public  TransferAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_transferhistory, viewGroup, false);
             TransferAdapter.VH viewHolder = new  TransferAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull  TransferAdapter.VH vh, int i) {
            vh.srtv.setText(String.valueOf(" "+(i+1)));
            vh.tv.setText("$ "+arrayList.get(i).get("Amount"));
            vh.tv1.setText(arrayList.get(i).get("Date"));
            vh.tv2.setText(arrayList.get(i).get("Remark"));
            vh.tv3.setText(arrayList.get(i).get("Type"));

            if (arrayList.get(i).get("Type").equals("Credit")){
                vh.tv3.setTextColor(Color.GREEN);
            }
            else {
                vh.tv3.setTextColor(Color.RED);
            }
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,srtv;
            //        CircleImageView img_icon;
            public VH(@NonNull View itemView) {
                super(itemView);
                srtv = itemView.findViewById(R.id.srtv);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
            }}}

}