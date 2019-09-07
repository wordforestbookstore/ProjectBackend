package com.eins.book.store.commons;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.eins.book.store.entity.Book;
import com.eins.book.store.entity.CartItem;
import com.eins.book.store.service.BookService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    private MailSenderInfo mailSenderInfo;

    private static BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public String initSummary(List<CartItem> cartItems) {
        String rtn = "";
        BigDecimal sum = BigDecimal.valueOf(0);
        for (CartItem cartItem : cartItems) {
            rtn += "<tr>";
            Long id = cartItem.getBookId();
            Book book = (Book) bookService.getBookByID(id);
            rtn += "<td>" + book.getTitle() + "</td>";
            rtn += "<td>" + book.getOurPrice() + "</td>";
            rtn += "<td>" + cartItem.getQty() + "</td>";
            rtn += "<td>" + cartItem.getSubtotal() + "</td>";
            rtn += "</tr>";
            sum = sum.add(cartItem.getSubtotal());
        }
        rtn += "<tr>" + "<td>" + "</td>" + "<td>" + "</td>" + "<th>" + "总计" + "</th>" + "<td>" + sum.doubleValue() + "</td>" + "</tr>";
        return rtn;
    }

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
                        (ConstantUtils.productionTip ? "http://49.235.51.43:8080/newUser?token=" : "http://localhost:8080/newUser?token=") + token +//完善信息链接
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

    public void sendcreatepassword(String address, String token, String initPassword){
        mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("smtp.qq.com");
        mailSenderInfo.setMailServerPort("465");
        mailSenderInfo.setUserName("1243299228@qq.com");
        mailSenderInfo.setPassword("mrnrfvzyynyafeeg");
        mailSenderInfo.setFromAddress("1243299228@qq.com");
        mailSenderInfo.setToAddress(address);

        mailSenderInfo.setValidate(true);

        mailSenderInfo.setSubject("辞林书店-找回密码");
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
                        (ConstantUtils.productionTip ? "http://49.235.51.43:8080/newUser?token=" : "http://localhost:8080/newUser?token=")+ token +//完善信息链接
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

    public void sendcart(String address, Long orderId, String username, String bAN, String bAS1, String bAS2, String bAC, String bAS, String bAZ, String pHN, String pT, String pCN, String pEM, String pEY, String sAN, String sAS1, String sAS2, String sAC, String sAS, String sAZ, String summary){
        mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("smtp.qq.com");
        mailSenderInfo.setMailServerPort("465");
        mailSenderInfo.setUserName("1243299228@qq.com");
        mailSenderInfo.setPassword("mrnrfvzyynyafeeg");
        mailSenderInfo.setFromAddress("1243299228@qq.com");
        mailSenderInfo.setToAddress(address);

        mailSenderInfo.setValidate(true);

        mailSenderInfo.setSubject("辞林书店-订单详情");
        Date date = new Date();
        String content = String.format(
                "<!DOCTYPE html>"+
                        "<html lang=\"en\">"+
                        "<head>"+
                        "<meta charset=\"UTF-8\" />"+
                        "<title></title>"+
                        "</head>"+
                        "<style type=\"text/css\">html,body{margin: 0;padding: 0;font-size: 14px;}.container{width: 880px;margin:0 auto;background: #e7f5ff;height:1200px;padding-top: 80px;margin-top: 20px;}.container-con{width:680px;margin:0 auto;background:#fff;height:1000px;padding:20px;}.eamil-top{font-size: 14px;}.eamil-top>span{color:#000;font-weight: bold;}.eamil-top2{font-size: 14px;padding-left: 16px;margin-bottom: 30px;}.eamil-con{padding:20px;}.eamil-con>p{line-height: 20px;}.top-img{background:url(\"images/tt0_03.png\") no-repeat;background-size: cover; width:722px;height:100px;margin:0 auto;}.fpptwe{line-height: 30px;}.footer{float: right;}.jingao{font-size: 12px;color:#888}</style>"+
                        "<body>"+
                        "<div class=\"container\">"+
                        "<div class=\"top-img\"></div>"+
                        "<div class=\"container-con\">"+
                        "<p class=\"eamil-top\">"+
                        "尊敬的"+username+
                        "</p>"+
                        "<div class=\"email-top\">"+
                        "<p>"+"感谢您选择辞林书店，希望我们无微不至的服务让您感到愉快"+"</p>"+
                        "<p>"+"您的订单号为#" + orderId + "。以下是您的详细订单："+"</p>"+
                        "<h2>"+"订单#" + orderId + "详情"+"</h2>"+
                        " <hr style=\"height:3px;border:none;border-top:1px ridge gray\"/>"+
                        "</div>"+
                        "<div class=\"eamil-top2\">"+
                        "<table border=\"1\">"+
                        "<tr>"+
                        "<th>"+"购物地址"+"</th>"+
                        "<th>"+"支付信息"+"</th>"+
                        "<th>"+"邮寄地址"+"</th>"+
                        "</tr>"+
                        "<tr>"+
                        "<td>" + bAN + "</td>"+
                        "<td>" + pHN + "</td>"+
                        "<td>" + sAN + "</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<td>" + bAS1 + " " + bAS2 + "</td>"+
                        "<td>" + pT + "</td>"+
                        "<td>" + sAS1 + " " + sAS2 + "</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<td>" + bAC + "</td>"+
                        "<td>" + pCN + "</td>"+
                        "<td>" + sAC + "</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<td>" + bAS + "</td>"+
                        "<td>" + pEM + "</td>"+
                        "<td>" + sAS + "</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<td>" + bAZ + "</td>"+
                        "<td>" + pEY + "</td>"+
                        "<td>" + sAZ + "</td>"+
                        "</tr>"+
                        "<h2>"+"订单小结"+"</h2>"+
                        "</table>"+
                        "</div>"+
                        "<div id=\"div1\">"+
                        "<table border=\"1\">"+
                        "<tr>"+
                        "<th>"+"书籍名称"+"</th>"+
                        "<th>"+"书籍价格"+"</th>"+
                        "<th>"+"书籍数量"+"</th>"+
                        "<th>"+"合计"+"</th>"+
                        "</tr>"+
                        summary+
                        "</table>"+
                        "<p>"+"Best,"+"</p>"+
                        "<p>"+"辞林 BookStore"+"</p>"+
                        "<p>"+"始于1923"+"</p>"+
                        "</div>"+
                        "</div>"+
                        "</div>"+
                        "</body>"+
                        "</html>", date, date);
        mailSenderInfo.setContent(content);

        SimpleMailSender sender = new SimpleMailSender();
        sender.sendHtmlMail(mailSenderInfo);
    }
}

