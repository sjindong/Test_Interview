package com.test_interview.Data;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sjd on 2017/8/7.
 */

public class DataUpload {

    /**
     * name : xxx
     * tel : xxx
     * timestamp : 0   时间戳（秒）
     * data : {"hcho":"0","tvoc":"1","pm25":"0","pm1":"1","pm10":"1","tem":"1","hum":"1"}
     */

    private String name;
    private String tel;
    private String timestamp;
    private DataBean data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataUpload getDefault(DataBean dataBean) {
        this.setData(dataBean);
        name = "ShengJinDong";
        tel = "13585899519";

        time =  (System.currentTimeMillis() / 1000L);
        timestamp = String.valueOf(time);
        return this;
    }

    long time;

    public JSONObject getJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("tel", tel);
            json.put("timestamp", time);
            json.put("data", data.getJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("SJD", json.toString());
        return json;
    }
}
