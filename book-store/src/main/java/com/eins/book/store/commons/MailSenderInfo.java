package com.eins.book.store.commons;

import java.util.List;
import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 邮件发送配置参数信息
 * @ClassName: MailSenderInfo
 */
public class MailSenderInfo {
    // 发送邮件的服务器的IP
    private String mailServerHost;
    //发送邮件的服务器端口，该处暂时默认25
    private String mailServerPort = "25";
    // 邮件发送者的地址
    private String fromAddress;
    // 邮件接收者的地址
    private String toAddress;
    //邮件接收者的地址集合
    private String[] toBatchAddress;
    // 登陆邮件发送服务器的用户名
    private String userName;
    //登陆邮件发送服务器的密码
    private String password;
    // 是否需要身份验证 [默认false不认证]
    private boolean validate = false;
    // 邮件主题
    private String subject;
    // 邮件主题
    private String[] subjects;
    // 邮件的文本内容
    private String content;
    // 邮件的文本内容
    private String[] contents;
    // 邮件附件的文件名
    private String[] attachFileNames;
    // 邮件附件的文件名 针对一邮件带多个附件关系
    private List<String[]> attachFileList;

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        try {
            p.put("mail.smtp.host", this.mailServerHost);
            p.put("mail.smtp.port", this.mailServerPort);
            p.put("mail.smtp.auth", validate ? "true" : "false");
            p.setProperty("mail.transport.protocol", "smtp");
            if("smtp.qq.com".equals(this.mailServerHost)) {
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                p.put("mail.smtp.ssl.enable", "true");
                p.put("mail.smtp.ssl.socketFactory", sf);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    /**
     * 获取发送邮件的服务器的IP  如：smtp.163.com
     */
    public String getMailServerHost() {
        return mailServerHost;
    }
    /**
     * 设置发送邮件的服务器的IP  如：smtp.163.com
     */
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }
    /**
     * 获取发送邮件的服务器端口，如：网易邮箱默认25
     */
    public String getMailServerPort() {
        return mailServerPort;
    }
    /**
     * 设置发送邮件的服务器端口，如：网易邮箱默认25
     */
    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }
    /**
     * 是否需要身份验证 [默认false不认证]
     */
    public boolean isValidate() {
        return validate;
    }
    /**
     * 设置身份验证 [默认false不认证]
     */
    public void setValidate(boolean validate) {
        this.validate = validate;
    }
    /**
     * 获取邮件附件的文件名
     */
    public String[] getAttachFileNames() {
        return attachFileNames;
    }
    /**
     * 设置邮件附件的文件名
     */
    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }
    /**
     * 获取邮件发送者的邮箱地址
     */
    public String getFromAddress() {
        return fromAddress;
    }
    /**
     * 设置邮件发送者的邮箱地址
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    /**
     * 获取邮件发送者的邮箱密码
     */
    public String getPassword() {
        return password;
    }
    /**
     * 设置邮件发送者的邮箱密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * 获取邮件接收者的地址
     */
    public String getToAddress() {
        return toAddress;
    }
    /**
     * 设置邮件接收者的地址
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    /**
     * 获取登陆邮件发送服务器的用户名
     */
    public String getUserName() {
        return userName;
    }
    /**
     * 设置登陆邮件发送服务器的用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * 获取邮件主题
     */
    public String getSubject() {
        return subject;
    }
    /**
     * 设置邮件主题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * 获取邮件主题
     */
    public String[] getSubjects() {
        return subjects;
    }
    /**
     * 设置邮件主题
     */
    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }
    /**
     * 获取邮件的文本内容
     */
    public String getContent() {
        return content;
    }
    /**
     * 设置邮件的文本内容
     */
    public void setContent(String textContent) {
        this.content = textContent;
    }
    /**
     * 获取邮件的文本内容
     */
    public String[] getContents() {
        return contents;
    }
    /**
     * 设置邮件的文本内容
     */
    public void setContents(String[] contents) {
        this.contents = contents;
    }
    /**
     * 针对一邮件多附件
     */
    public List<String[]> getAttachFileList() {
        return attachFileList;
    }
    /**
     * 针对一邮件多附件
     */
    public void setAttachFileList(List<String[]> attachFileList) {
        this.attachFileList = attachFileList;
    }
}
