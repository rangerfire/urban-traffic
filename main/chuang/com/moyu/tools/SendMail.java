package com.moyu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.moyu.entity.Comment;

public class SendMail {
	public static void main(String[] args) {
		Send("1","ai727249256@qq.com","mmd","D:\\Workspaces\\MyEclipse Professional 2014\\AirplaneOrderWeb\\src\\com\\moyu\\dao\\mail.properties");
	}
	public static boolean Send(String name, String email, String content,String path) {
		String smtphost = null; // 发送邮件服务器
		String user = null; // 邮件服务器登录用户名
		String password = null; // 邮件服务器登录密码
		String from = null; // 发送人邮件地址
		String to = email; // 接受人邮件地址
		String subject = null; // 邮件标题
		String body = null; // 邮件内容
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(path));
			smtphost = pro.getProperty("mail.smtp");
			user = pro.getProperty("mail.user");
			password = pro.getProperty("mail.password");
			from = pro.getProperty("mail.sender");
			subject = pro.getProperty("mail.subject");
			body = pro.getProperty("mail.body");
			System.out.println(smtphost);
			System.out.println(password);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = new InternetAddress(from);
			message.setFrom(fromAddress);
			InternetAddress toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setText("尊敬的<span style='color:red'>" + name + "</span>," + body
					+ "<br>您的留言内容为:&nbsp;&nbsp;<span style='color:red'>"
					+ content + "</span>", "gbk", "html");
			Transport transport = session.getTransport("smtp");
			transport.connect(smtphost, user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
