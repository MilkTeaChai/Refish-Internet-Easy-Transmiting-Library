package org.refish.rietlib;

import java.util.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.InputStream;

public class http {
        public void http(URL url,String msg){
            new Thread(){
                public HttpURLConnection conn;
                public String ODS;
                public void run() {
                        conn =(HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connect-Type","application/x-www-from-urlencoded");
                        conn.setRequestProperty("Connect-Length",msg.length() + "");
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        os.write(msg.getBytes());
                        int code = conn.getResponseCode();
                        if(code==200){
                            InputStream ODS =conn.getInputStream();
                        }else{
                            System.err.println("Error in the HTTP");
                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }conn.disconnect();
                }
            }.start();
        }
    }