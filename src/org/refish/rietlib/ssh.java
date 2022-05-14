package org.refish.rietlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class ssh {
    private static String DEFAULT_CHAR_SET = "UTF-8";

    protected static Connection login(String ip, String userName, String password) {
        boolean isAuthenticated = false;
        Connection conn = null;
        long startTime = Calendar.getInstance().getTimeInMillis();
        try {
            conn = new Connection(ip);
            conn.connect(); // 连接主机

            isAuthenticated = conn.authenticateWithPassword(userName, password); // 认证
            if (isAuthenticated) {
                System.out.println("认证成功");
            } else {
                System.out.println("认证失败");
            }
        } catch (IOException e) {
            System.out.println("登录失败");
            e.printStackTrace();
        }
        return conn;
    }

    protected static String execute(Connection conn, String cmd) {
        String result = "";
        Session session = null;
        try {
            if (conn != null) {
                session = conn.openSession();  // 打开一个会话
                session.execCommand(cmd);      // 执行命令
                result = processStdout(session.getStdout(), DEFAULT_CHAR_SET);

                //如果为得到标准输出为空，说明脚本执行出错了
                if (result == null) {
                    System.out.println("[空指针异常]");
                    result = processStdout(session.getStderr(), DEFAULT_CHAR_SET);
                } else {
                    System.out.println("[执行命令成功]");
                }
            }
        } catch (IOException e) {
            System.err.println("[执行命令失败]" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    private static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public void ssh(String ip, String userName, String password, String cmd) {
        Connection conn = SshUtil.login(ip, userName, password);
        String result = ssh.execute(conn, cmd);
        String getback = result;
    }
}
