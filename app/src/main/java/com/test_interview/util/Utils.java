package com.test_interview.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sjd on 2017/8/8.
 */

public class Utils {
    /**
     * 读取本地文件数据
     *
     * @param path
     */
    public static byte[] readFile(String path) {
        String s = Environment.getExternalStorageDirectory().getPath();
        File f = new File(s + path);
        if (!f.exists()) {
            Log.e("SJD", "file is not exist");
            return null;
        }
        byte[] b = new byte[(int) f.length()];
        try {
            FileInputStream inPut = new FileInputStream(f);

            inPut.read(b);
            inPut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 以大端模式将int转成byte[]
     */
    public static byte[] intToBytesBig(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 以小端模式将int转成byte[]
     *
     * @param value
     * @return
     */
    public static byte[] intToBytesLittle(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 以大端模式将byte[]转成int
     */
    public static int bytesToIntBig(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    /**
     * 以小端模式将byte[]转成int
     */
    public static int bytesToIntLittle(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    public static int bytesToIntBig2(byte src, byte src1, byte src2) {
        int value;
        value = (int) (((src & 0xFF) << 16)
                | ((src1 & 0xFF) << 8)
                | (src2 & 0xFF));
        return value;
    }

    public static int bytesToIntBig2(byte src, byte src1) {
        int value;
        value = (int) (((src & 0xFF) << 8)
                | (src1 & 0xFF));
        return value;
    }

    public static int bytesToIntBig2(byte src) {
        int value;
        value = (int) (src & 0xFF);
        return value;
    }

    public static String getHmacMd5Str(String keyString, String s) {
        String sEncodedString = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return sEncodedString;
    }


    public static String hmacSha1(String key, String base) {
        if (TextUtils.isEmpty(base) || TextUtils.isEmpty(key)) {
            return "";
        }
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = null;
        try {
            mac = Mac.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(secret);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] digest = mac.doFinal(base.getBytes());

        return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    public static void saveToken(Context context, String key, String s) {
        SharedPreferences preferences = context.getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, s);
        editor.commit();
    }

    public static String readToken(Context context, String key, String defaulteString) {
        SharedPreferences preferences = context.getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
        return preferences.getString(key, defaulteString);
    }
}
