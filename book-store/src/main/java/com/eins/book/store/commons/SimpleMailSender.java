package com.eins.book.store.commons;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 邮件发送器
 * @ClassName: SimpleMailSender
 */
public class SimpleMailSender {

    /**
     * 以文本格式发送邮件
     * @param mailInfo 待发送的邮件的信息
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MailAuthenticator(mailInfo.getUserName(),
                    mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session
                .getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            if (mailInfo.getToAddress() != null
                    && mailInfo.getToAddress().length() > 0) {
                Address to = new InternetAddress(mailInfo.getToAddress());
                // Message.RecipientType.TO属性表示接收者的类型为TO
                mailMessage.setRecipient(Message.RecipientType.TO, to);
            }
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 以HTML格式发送邮件
     * @param mailInfo 待发送的邮件信息
     */
    public boolean sendHtmlMail(MailSenderInfo mailInfo) {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MailAuthenticator(mailInfo.getUserName(),
                    mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session
                .getDefaultInstance(pro, authenticator);
        try {
            sendMailSession.setDebug(true);
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            if (mailInfo.getToAddress() != null
                    && mailInfo.getToAddress().length() > 0) {
                Address to = new InternetAddress(mailInfo.getToAddress());
                // Message.RecipientType.TO属性表示接收者的类型为TO
                mailMessage.setRecipient(Message.RecipientType.TO, to);
            }
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSubject(MimeUtility.encodeText(mailInfo.getSubject(), "UTF-8", "B"));
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();

            //-------------------------------beigin 文本---------------------//
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            //----------------------------------end 文本---------------------//

            //-------------------------------beigin 附件---------------------//
            if(mailInfo.getAttachFileList() != null && mailInfo.getAttachFileList().size() > 0){
                for (String[] files : mailInfo.getAttachFileList()) {
                    for (String file : files) {
                        //邮件的附件
                        String fileName = file;
                        if(fileName != null&&!fileName.trim().equals("")) {
                            MimeBodyPart mbp = new MimeBodyPart();
                            FileDataSource fileSource = new FileDataSource(fileName);
                            mbp.setDataHandler(new DataHandler(fileSource));
                            try {
                                mbp.setFileName(MimeUtility.encodeText(fileSource.getName()));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            mainPart.addBodyPart(mbp);
                        }
                    }
                }
            } else {
                if(mailInfo.getAttachFileNames() != null && mailInfo.getAttachFileNames().length > 0){
                    //邮件的附件
                    String fileName = mailInfo.getAttachFileNames()[0];
                    if(fileName != null&&!fileName.trim().equals("")) {
                        MimeBodyPart mbp = new MimeBodyPart();
                        FileDataSource fileSource = new FileDataSource(fileName);
                        mbp.setDataHandler(new DataHandler(fileSource));
                        try {
                            mbp.setFileName(MimeUtility.encodeText(fileSource.getName()));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        mainPart.addBodyPart(mbp);
                    }
                }
            }

            //----------------------------------end 附件---------------------//

            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            System.out.println("发送成功！");
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * 以HTML格式发送多封邮件
     * @param mailInfo 待发送的邮件信息
     */
    public boolean sendBatchHtmlMail(MailSenderInfo mailInfo)  {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        pro.setProperty("mail.transport.protocol", "smtp");
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MailAuthenticator(mailInfo.getUserName(),
                    mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getInstance(pro, authenticator);
        try {
            // 发送邮件
            sendMailSession.setDebug(true);
            Transport transport = sendMailSession.getTransport();
            transport.connect(mailInfo.getMailServerHost(),Integer.parseInt(mailInfo.getMailServerPort()),
                    mailInfo.getUserName(), mailInfo.getPassword());
            // 创建邮件的接收者地址，并设置到邮件消息中
            transport.close();
            System.out.println("发送成功！");
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
