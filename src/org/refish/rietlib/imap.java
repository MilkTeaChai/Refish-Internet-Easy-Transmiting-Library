package org.refish.rietlib;

import java.net.URL;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class imap {
        public void imap(String host,int port, String username, String password) {
            Properties props = System.getProperties();
            props.setProperty("mail.imap.socketFactory.class", null);
            props.setProperty("mail.imap.socketFactory.port",port);
            props.setProperty("mail.store.protocol","imap");
            props.setProperty("mail.imap.host", host);
            props.setProperty("mail.imap.port", port);
            props.setProperty("mail.imap.auth.login.disable", "true");
            Session session = Session.getDefaultInstance(props,null);
            session.setDebug(false);
            IMAPFolder folder= null;
            IMAPStore store=null;
            try {
                store=(IMAPStore)session.getStore("imap");  // 使用imap会话机制，连接服务器
                store.connect(host,port,username,password);
                folder=(IMAPFolder)store.getFolder("Sent Messages"); //收件箱

                Folder defaultFolder = store.getDefaultFolder();
                Folder[] allFolder = defaultFolder.list();
                for (int i = 0; i < allFolder.length; i++) {
                    System.out.println("这个是服务器中的文件夹="+allFolder[i].getFullName());
                }
                // 使用只读方式打开收件箱
                folder.open(Folder.READ_WRITE);
                int size = folder.getMessageCount();
                System.out.println("这里是打印的条数=="+size);
                Message[] mess=folder.getMessages();
                //  Message message = folder.getMessage(size);
                for (int i = 0; i <5; i++) {
                    String from = mess[i].getFrom()[0].toString();
                    String subject = mess[i].getSubject();
                    Date date = mess[i].getSentDate();
                    System.out.println("From: " + from);
                    System.out.println("Subject: " + subject);
                    System.out.println("Date: " + date);
                }
               /* String from = message.getFrom()[0].toString();
                String subject = message.getSubject();
                Date date = message.getSentDate();*/
                /* BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); */

            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (folder != null) {
                        folder.close(false);
                    }
                    if (store != null) {
                        store.close();
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("接收完毕！");
        }
    }
