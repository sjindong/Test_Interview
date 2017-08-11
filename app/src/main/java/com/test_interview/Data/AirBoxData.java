package com.test_interview.Data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import static com.test_interview.util.Utils.bytesToIntBig2;

public class AirBoxData {
    public ArrayList<DataAirBox> arrayData;
    public AirBoxData( byte[] b) {{
        if (b == null) {
            return;
        }else {
            init(b);
        }
    }}
    public void init( byte[] b) {
        //将byte数据 按照20个字节进行划分
        ArrayList<byte[]> arrayList = new ArrayList<>();
        byte[] a = new byte[20];
        int max = (int) Math.ceil(b.length / 20);
        for (int i = 0; i < max; i++) {
            if (i != max - 1)
                System.arraycopy(b, i * 20, a, 0, 20);
            else {
                int last = b.length % 20;
                if (last == 0) {
                    last = 20;
                }
                a = new byte[last];
                System.arraycopy(b, i * 20, a, 0, last);
            }
            arrayList.add(a);
        }

        transToDataAirBox(arrayList);
    }

    //对数据 转换成 DataAirBox参数
    private void transToDataAirBox(ArrayList<byte[]> arrayList) {
        arrayData = new ArrayList<>();
        DataAirBox dataAirBox = new DataAirBox();
        for (byte[] c : arrayList) {
            dataAirBox.setHeader(bytesToIntBig2(c[0], c[1]));//头
            dataAirBox.setCommand(bytesToIntBig2(c[2], c[3]));//command
            dataAirBox.setLength(bytesToIntBig2(c[4]));//长度
            dataAirBox.setDataBean(new DataBean(
                    bytesToIntBig2(c[5]), //温度
                    bytesToIntBig2(c[6]),//湿度
                    bytesToIntBig2(c[7], c[8]),//甲醛
                    bytesToIntBig2(c[9], c[10]),//TVOC
                    bytesToIntBig2(c[11], c[12]),//PM2.5
                    bytesToIntBig2(c[13], c[14]),//PM1
                    bytesToIntBig2(c[15], c[16])//PM10
            ));
            dataAirBox.setChecksum(bytesToIntBig2(c[17]));//校验sum
//            Log.e("SJd", dataAirBox.toString() +"    0:"+ String.valueOf(c[0]) +"  1:"+ String.valueOf(c[1]));
            arrayData.add(dataAirBox);
        }
    }

}
