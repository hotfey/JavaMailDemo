package com.hotfey.java.mail.demo;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	private static String SMTP_HOST_NAME;
	private static String SMTP_HOST_PORT;
	private static String SMTP_HOST_AUTH;
	private static String SMTP_AUTH_USER;
	private static String SMTP_AUTH_PWD;

	public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
		boolean debug = true;

		// Set the host SMTP address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.port", SMTP_HOST_PORT);
		props.put("mail.smtp.auth", SMTP_HOST_AUTH);

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		}
	}

	public static void main(String args[]) throws Exception {
		String[] emailList;

		String emailMsgTxt;
		String emailSubjectTxt;

		String emailFromAddress;

		if (args.length > 4) {
			emailList = args[0].split(",");
			emailSubjectTxt = args[1];
			emailMsgTxt = args[2];
			emailFromAddress = args[3];

			String[] base = args[4].split(",");
			if (base.length == 5) {
				SMTP_HOST_NAME = base[0];
				SMTP_HOST_PORT = base[1];
				SMTP_HOST_AUTH = base[2];
				SMTP_AUTH_USER = base[3];
				SMTP_AUTH_PWD = base[4];
			} else {
				System.out.println("base:<host> <port> <auth> <user> <pwd>");
				return;
			}
		} else {
			System.out.println("Usage:<to> <sub> <msg> <from> <base>");
			return;
		}
		MailSender mailSender = new MailSender();
		mailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
	}

}
