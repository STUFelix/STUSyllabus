package com.example.daidaijie.syllabusapplication.bean;

import com.example.daidaijie.syllabusapplication.util.GsonUtil;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by daidaijie on 2016/10/13.
 */

public class BannerBeen extends RealmObject {
    private int timestamp;
    /**
     * id : 0
     * link : http://119.29.95.245:8000/youzhiyouheng.jpg
     * description : 有志有恒咯
     * url : http://119.29.95.245:8000/youzhiyouheng.jpg
     */

//    private String mBannersString;
    private String bannersString;

    @SerializedName("notifications")
    @Ignore
    private List<Banner> banners;
//    private List<Banner> mBanners;

    public void setBannersString() {
        bannersString = GsonUtil.getDefault().toJson(banners);
    }

    public String getBannersString() {
        return bannersString;
    }

    public void setBanners() {
        banners = GsonUtil.getDefault().fromJson(bannersString,
                new TypeToken<List<Banner>>() {
                }.getType());
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

//    public void setBanners(List<Banner> banners) {
//        mBanners = banners;
//    }
}
