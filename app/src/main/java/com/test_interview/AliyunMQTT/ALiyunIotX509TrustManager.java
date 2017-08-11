/**
 * aliyun.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.test_interview.AliyunMQTT;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.test_interview.R;

import java.io.InputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * @version $Id: ALiyunIotX509TrustManager.java, v 0.1 2017年3月10日 下午4:34:07 lvjianwen Exp $
 */


@TargetApi(Build.VERSION_CODES.N)
public class ALiyunIotX509TrustManager extends X509ExtendedTrustManager{
    
    //根证书认证
    private X509TrustManager rootTrusm;

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public ALiyunIotX509TrustManager(Context context) throws Exception{
         this.context = context;
         //CA根证书，可以从官网下载
         InputStream in =context.getResources().openRawResource(R.raw.root) ;
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         Certificate ca = null;
         try {
             ca = cf.generateCertificate(in);
         } catch (CertificateException e) {
            throw e;
         } finally {
             in.close();
         }
         String keyStoreType = KeyStore.getDefaultType();
         KeyStore keyStore = KeyStore.getInstance(keyStoreType);
         keyStore.load(null, null);
         keyStore.setCertificateEntry("ca", ca);
         String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
         TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
         tmf.init(keyStore);
         
         rootTrusm = (X509TrustManager) tmf.getTrustManagers()[0];
    
    }

   

    @Override
    public void checkClientTrusted(X509Certificate[] arg0,
                                   String arg1) throws CertificateException {
        
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain,
                                   String authType) throws CertificateException {
        
      //验证服务器证书合法性
        rootTrusm.checkServerTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType,
                                   Socket socket) throws CertificateException {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType,
                                   SSLEngine engine) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType,
                                   Socket socket) throws CertificateException {
        
        //验证服务器证书合法性
        rootTrusm.checkServerTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType,
                                   SSLEngine engine) throws CertificateException {
      //验证服务器证书合法性
        rootTrusm.checkServerTrusted(chain, authType);
    }

}
