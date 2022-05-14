package org.refish.rietlib;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

public class telnet {
    public telnet(String host,int port,String command) {
            try {
                TelnetClient telnetClient = new TelnetClient("vt200");  //指明Telnet终端类型，否则会返回来的数据中文会乱码
                telnetClient.setDefaultTimeout(5000); //socket延迟时间：5000ms
                telnetClient.connect(host,port);  //建立一个连接,默认端口是23
                InputStream inputStream = telnetClient.getInputStream(); //读取命令的流
                PrintStream pStream = new PrintStream(telnetClient.getOutputStream());  //写命令的流
                byte[] b = new byte[2048];
                int size;
                StringBuffer sBuffer = new StringBuffer(300);
                while(true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
                    size = inputStream.read(b);
                    if(-1 != size) {
                        sBuffer.append(new String(b,0,size));
                        if(sBuffer.toString().trim().endsWith("login:")) {
                            break;
                        }
                    }
                }
                System.out.println(sBuffer.toString());
                pStream.println(command); //写命令
                pStream.flush(); //将命令发送到telnet Server
                if(null != pStream) {
                    pStream.close();
                }
                telnetClient.disconnect();
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
}
