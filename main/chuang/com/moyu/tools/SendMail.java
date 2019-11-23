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
		String smtphost = null; // �����ʼ�������
		String user = null; // �ʼ���������¼�û���
		String password = null; // �ʼ���������¼����
		String from = null; // �������ʼ���ַ
		String to = email; // �������ʼ���ַ
		String subject = null; // �ʼ�����
		String body = null; // �ʼ�����
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
			message.setText("�𾴵�<span style='color:red'>" + name + "</span>," + body
					+ "<br>������������Ϊ:&nbsp;&nbsp;<span style='color:red'>"
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
