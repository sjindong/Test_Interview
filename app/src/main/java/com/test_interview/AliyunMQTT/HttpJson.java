package com.test_interview.AliyunMQTT;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sjd on 2017/8/8.
 */

public class HttpJson {

    public HttpJson() {

    }

    public HttpJson(String s) {
        analysisHttpResponse(s);
    }

    public void analysisHttpResponse(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            if (jsonObject.has("iotId")) {
                iotId = jsonObject.optString("iotId");
            }
            if (jsonObject.has("iotToken")) {
                iotToken = jsonObject.optString("iotToken");
            }
            if (jsonObject.has("resources")) {
                JSONObject r = new JSONObject(jsonObject.optString("resources"));
                if (r.has("mqtt")) {
                    JSONObject mqtt = new JSONObject(jsonObject.optString("mqtt"));
                    ResourcesBean.MqttBean mqttBean = new ResourcesBean.MqttBean();
                    if (mqtt.has("host")) {
                        mqttBean.setHost(jsonObject.optString("host"));
                    }
                    if (mqtt.has("port")) {
                        mqttBean.setPort(jsonObject.optInt("port"));
                    }
                    resources.setMqtt(mqttBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * iotId : 42Ze0mk3556498a1AlTP
     * iotToken : 0d7fdeb9dc1f4344a2cc0d45edcb0bcb
     * resources : {"mqtt":{"host":"xxx.iot-as-mqtt.cn-shanghai.aliyuncs.com","port":1883}}
     */

    private String iotId;
    private String iotToken;
    private ResourcesBean resources;

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getIotToken() {
        return iotToken;
    }

    public void setIotToken(String iotToken) {
        this.iotToken = iotToken;
    }

    public ResourcesBean getResources() {
        return resources;
    }

    public void setResources(ResourcesBean resources) {
        this.resources = resources;
    }

    public static class ResourcesBean {
        /**
         * mqtt : {"host":"xxx.iot-as-mqtt.cn-shanghai.aliyuncs.com","port":1883}
         */

        private MqttBean mqtt;

        public MqttBean getMqtt() {
            return mqtt;
        }

        public void setMqtt(MqttBean mqtt) {
            this.mqtt = mqtt;
        }

        public static class MqttBean {
            /**
             * host : xxx.iot-as-mqtt.cn-shanghai.aliyuncs.com
             * port : 1883
             */

            private String host;
            private int port;

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }
        }
    }
}
