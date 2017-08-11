package com.test_interview.AliyunMQTT;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test_interview.util.SignUtil.bytesToHexString;
import static com.test_interview.util.SignUtil.encryptHMACbase64;
import static com.test_interview.util.SignUtil.getLocalMacAddressFromWifiInfo;
import static com.test_interview.util.Utils.getHmacMd5Str;
import static com.test_interview.util.Utils.saveToken;

/**
 * Created by sjd on 2017/8/8.
 */
public class HttpRegister {
    Context context;

    public HttpRegister(Context context) {
        this.context = context;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    String string = (String) msg.obj;
                    Log.e("SJD", "httpGet = " + string);
                    HttpJson httpJson = new HttpJson(string);
                    saveToken(context, "iotId", httpJson.getIotId());
                    saveToken(context, "iotToken", httpJson.getIotToken());
                    saveToken(context, "host", httpJson.getResources().getMqtt().getHost());
                    saveToken(context, "port", String.valueOf(httpJson.getResources().getMqtt().getPort()));
                    break;
                case 222:
                    String string1 = (String) msg.obj;
                    Log.e("SJD", string1);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void httpGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpsGet2();
            }
        }).start();
    }

    private void httpsGet2() {
        try {
            String clientId = Contain.ClientId;
            String a = "clientId" + clientId +
                    "deviceName" + Contain.deviceName +
                    "productKey" + Contain.ProductKey +
                    //"timestamp"+时间戳+
                    "";
            String sign = bytesToHexString(encryptHMACbase64("hmacsha1", a, Contain.deviceSecret));
            Log.e("SJD", bytesToHexString(encryptHMACbase64("hmacsha1", "clientId12345deviceNamedeviceproductKeypktimestamp789", "secret")));

            String url = "https://iot-auth.cn-shanghai.aliyuncs.com/auth/" + Contain.deviceName;
            url +=
                    //Contain.Topic +
                    "?productKey=" + Contain.ProductKey +
                            "&sign=" + sign +
                            "&version=" + "default" +
                            "&clientId=" + Contain.ClientId +
                            "&resouces=" + "mqtt" +
                            // "&timestamp=" + 123 +
                            "&deviceName=" + Contain.deviceName;
            URL url1 = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) url1.openConnection();
            urlConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setConnectTimeout(5 * 1000);
            // 设置请求方式为 PUT
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Charset", "UTF-8");
            urlConn.connect();

            if (urlConn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String str;
                StringBuffer sb = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                Log.i("esasdasd", "result:" + sb.toString());
                Message message = new Message();
                message.what = 111;
                message.obj = sb.toString();
                handler.sendMessage(message);
                urlConn.disconnect();
            } else {
                String error;
                switch (urlConn.getResponseCode()) {
                    case 401:
                        error = "在这个场景里面通常在签名匹配不通过时返回";
                        break;
                    case 460:
                        error = "参数异常";
                        break;
                    case 500:
                        error = "一些未知异常";
                        break;
                    case 5001:
                        error = "指定的设备不存在";
                        break;
                    case 6200:
                        error = "未授权认证类型错误";
                        break;
                    default:
                        error = "未知错误";
                }
                Log.e("SJD", urlConn.getResponseCode() + "  " + error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
