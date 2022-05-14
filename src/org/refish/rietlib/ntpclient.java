package org.refish.rietlib;


import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ntpclient {
    private NTPUDPClient ntpudpClient = null;
    private InetAddress inetAddress = null;
    private boolean isInit = false;

    public void ntp(String url, int timeout) {
        this.ntpudpClient = new NTPUDPClient();
        //设置连接超时时间
        this.ntpudpClient.setDefaultTimeout(timeout);
        try {
            this.inetAddress = InetAddress.getByName(url);
            this.isInit = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        if (this.isInit) {
            try {
                TimeInfo timeInfo = this.ntpudpClient.getTime(this.inetAddress);
                TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
                Date date = timeStamp.getDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return simpleDateFormat.format(date);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void stopNTPClient() {
        this.isInit = false;
        if (null != this.ntpudpClient)
            this.ntpudpClient.close();
    }
}
