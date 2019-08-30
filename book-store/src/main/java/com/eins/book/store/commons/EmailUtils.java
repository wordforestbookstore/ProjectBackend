package com.eins.book.store.commons;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class EmailUtils {
    private MailSenderInfo mailSenderInfo;

    public void sendTextEmail() throws Exception {
        SimpleMailSender sender = new SimpleMailSender();
        sender.sendTextMail(mailSenderInfo);
    }

    public void sendHtmlEmail() throws Exception {
        SimpleMailSender sender = new SimpleMailSender();
        sender.sendHtmlMail(mailSenderInfo);
    }

    public void sendTextEmail1() throws Exception {
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "false");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 使用 STARTTLS安全连接
        props.put("mail.smtp.starttls.enable", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.smtp.host", mailSenderInfo.getMailServerHost());
        // 设置邮件服务器端口
        props.put("mail.smtp.port", mailSenderInfo.getMailServerPort());
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        // 如果需要身份认证，则创建一个密码验证器
        MailAuthenticator authenticator = new MailAuthenticator(mailSenderInfo.getUserName(),
                mailSenderInfo.getPassword());
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getDefaultInstance(props, authenticator);

        Message msg = new MimeMessage(session);
        msg.setSubject("主题-你猜猜？");
        StringBuilder builder = new StringBuilder();
        builder.append("测试邮件： 我用Java代码给你发送了一份邮件！我的❤你收到了吗？");
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress(mailSenderInfo.getUserName()));
        Address to = new InternetAddress(mailSenderInfo.getToAddress());
        // Message.RecipientType.TO属性表示接收者的类型为TO
        msg.setRecipient(Message.RecipientType.TO, to);

        Transport.send(msg);
    }
    public void sendcreate(String address, String token, String initPassword){
        mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("smtp.qq.com");
        mailSenderInfo.setMailServerPort("465");
        mailSenderInfo.setUserName("1243299228@qq.com");
        mailSenderInfo.setPassword("mrnrfvzyynyafeeg");
        mailSenderInfo.setFromAddress("1243299228@qq.com");
        mailSenderInfo.setToAddress(address);

        mailSenderInfo.setValidate(true);

        mailSenderInfo.setSubject("辞林书店-新用户");
        Date date = new Date();
        String content = String.format(
                "<!DOCTYPE html>"+
                        "<html lang=\"en\">"+
                        "<head>"+
                        "<meta charset=\"UTF-8\" />"+
                        "<title></title>"+
                        "</head>"+
                        "<style type=\"text/css\">html,body{margin: 0;padding: 0;font-size: 14px;}.container{width: 880px;margin:0 auto;background: #e7f5ff;height:800px;padding-top: 80px;margin-top: 20px;}.container-con{width:680px;margin:0 auto;background:#fff;height:600px;padding:20px;}.eamil-top{font-size: 14px;}.eamil-top>span{color:#000;font-weight: bold;}.eamil-top2{font-size: 14px;padding-left: 16px;margin-bottom: 30px;}.eamil-con{padding:20px;}.eamil-con>p{line-height: 20px;}.top-img{background:url(\"images/tt0_03.png\") no-repeat;background-size: cover; width:722px;height:100px;margin:0 auto;}.fpptwe{line-height: 30px;}.footer{float: right;}.jingao{font-size: 12px;color:#888}</style>"+
                        "<body>"+
                        "<div class=\"container\">"+
                        "<div class=\"top-img\"></div>"+
                        "<div class=\"container-con\">"+
                        "<p class=\"eamil-top\">"+
                        "http://localhost:8080/newUser?token="+ token +//完善信息链接
                        "</p>"+
                        "<p class=\"eamil-top2\">您好！</p>"+
                        "<div class=\"eamil-con\">"+
                        "<p>请单击链接完成邮箱验证，并完善您的个人信息。</p>"+
                        "<p>"+
                        "您的验证密码为：<span>%s</span>"+//验证密码
                        "</p>"+
                        //"<img src='http://img.mp.itc.cn/upload/20160326/73a64c935e7d4c9594bdf86d76399226_th.jpg' />"+
                        "</div>"+
                        "<p class=\"jingao\">（这是一封系统自动发送的邮件，请不要直接回复。）</p>"+
                        "<div class=\"footer\">"+
                        "<p></p>"+
                        "<span>%tF %tT</span>"+
                        "</div>"+
                        "</p>"+
                        "</div>"+
                        "</div>"+
                        "</body>"+
                        "</html>", initPassword, date, date);
        mailSenderInfo.setContent(content);

        SimpleMailSender sender = new SimpleMailSender();
        sender.sendHtmlMail(mailSenderInfo);
    }
}

