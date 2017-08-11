package com.test_interview.Data;

/**
 * Created by sjd on 2017/8/8.
 */

public class DataAirBox {
    int header;
    int command;
    int length;
    DataBean dataBean;
    int checksum;

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    public String toString() {
        return " header:" + header +
                " command:" + command +
                " length:" + length +
                " dataBean:" + dataBean.toString() +
                " checksum:" + checksum;
    }
}
