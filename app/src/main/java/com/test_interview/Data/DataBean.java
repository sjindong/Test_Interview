package com.test_interview.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sjd on 2017/8/8.
 */

public class DataBean {
    /**
     * hcho : 0   甲醛
     * tvoc : 1
     * pm25 : 0   PM2.5
     * pm1 : 1    PM1
     * pm10 : 1   PM10
     * tem : 1    温度
     * hum : 1    湿度
     */

    private String hum;
    private String tem;
    private String hcho;
    private String tvoc;
    private String pm25;
    private String pm1;
    private String pm10;


    public DataBean(String hum, String tem, String hcho, String tvoc, String pm25, String pm1, String pm10) {
        this.hum = hum;
        this.tem = tem;
        this.hcho = hcho;
        this.tvoc = tvoc;
        this.pm25 = pm25;
        this.pm1 = pm1;
        this.pm10 = pm10;
    }

    public DataBean(int hum, int tem, int hcho, int tvoc, int pm25, int pm1, int pm10) {
        this.hum = String.valueOf(hum);
        this.tem = String.valueOf(tem);
        this.hcho = String.valueOf(hcho);
        this.tvoc = String.valueOf(tvoc);
        this.pm25 = String.valueOf(pm25);
        this.pm1 = String.valueOf(pm1);
        this.pm10 = String.valueOf(pm10);
    }

    public String getHcho() {
        return hcho;
    }

    public void setHcho(String hcho) {
        this.hcho = hcho;
    }

    public String getTvoc() {
        return tvoc;
    }

    public void setTvoc(String tvoc) {
        this.tvoc = tvoc;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm1() {
        return pm1;
    }

    public void setPm1(String pm1) {
        this.pm1 = pm1;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String toString() {
        return " { hum:" + hum +
                " tem:" + tem +
                " hcho:" + hcho +
                " tvoc:" + tvoc +
                " pm25:" + pm25 +
                " pm1:" + pm1 +
                " pm10:" + pm10+
                " }";
    }

    public JSONObject getJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hum",hum);
            jsonObject.put("tem",tem);
            jsonObject.put("hcho",hcho);
            jsonObject.put("tvoc",tvoc);
            jsonObject.put("pm25",pm25);
            jsonObject.put("pm1",pm1);
            jsonObject.put("pm10",pm10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
