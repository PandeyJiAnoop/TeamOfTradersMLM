package com.sign.akp_teamoftraders;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class BannerData implements Serializable {
    String Banner;

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String Banner) {
        this.Banner = Banner;
    }

    public static List<BannerData> createJsonInList(String str){
        Gson gson=new Gson();
        Type type=new TypeToken<List<BannerData>>(){
        }.getType();
        return gson.fromJson(str,type);
    }
}
