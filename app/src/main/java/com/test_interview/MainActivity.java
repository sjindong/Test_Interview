package com.test_interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.test_interview.AliyunMQTT.Contain;
import com.test_interview.AliyunMQTT.SimpleClient4IOT;
import com.test_interview.Data.AirBoxData;
import com.test_interview.Data.DataBean;
import com.test_interview.Data.DataUpload;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static com.test_interview.util.Utils.readFile;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    AirBoxData airBoxData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        String fileName = "/Test_Interview/AirBoxData.sens";
        byte[] b = readFile(fileName);
        airBoxData = new AirBoxData(b);

        textView.setText(b.toString());

//        HttpRegister httpRegister = new HttpRegister(getApplicationContext());
//        httpRegister.httpGet();
//        Intent intent = new Intent(this, MQTTService.class);
//        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                SimpleClient4Shadow simpleClient4Shadow = new SimpleClient4Shadow(getApplicationContext());
                SimpleClient4IOT simpleClient4IOT = new SimpleClient4IOT(getApplicationContext());
                DataUpload dataUpload = new DataUpload();
                dataUpload.getDefault(airBoxData.arrayData.get(0).getDataBean());
                JSONObject jsonObject = dataUpload.getJsonObject();
                try {
                    simpleClient4IOT.sendMessage(jsonObject.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
