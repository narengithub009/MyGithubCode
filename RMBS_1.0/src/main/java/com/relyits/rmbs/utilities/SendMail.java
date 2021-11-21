package com.relyits.rmbs.utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail 
{
	public static String sendMail(String email,String userPassword,String usersName) throws Exception {
		//code for send mail

		final String username = "relyrmbs@gmail.com";
		final String password = "rely@1234";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");

		javax.mail.Session mailsession = javax.mail.Session.getInstance(props,
				new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(mailsession);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
		message.setSubject("Login password of "+" "+usersName);
		message.setContent("Hi "+" "+usersName+"&nbsp;<br/><br/>Your password is"+userPassword,"text/html");
		Transport.send(message);
		String empemail=email;
		return empemail;
	}
	public static String sendMail(String email,final String userPassword,final String usersName,String body){
		//code for send mail

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");

		javax.mail.Session mailsession = javax.mail.Session.getInstance(props,
				new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(usersName, userPassword);
			}
		});

		Message message = new MimeMessage(mailsession);
		try {
			message.setFrom(new InternetAddress(usersName));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Login password of "+" "+usersName);
			message.setContent(body,"text/html");
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
			System.out.println("&&&&&&&&&&&&&catch block calling$$$$$$$$$$$$$$$$"+e.getMessage());
		}
		
		
		String empemail=email;
		return empemail;
	}
}
