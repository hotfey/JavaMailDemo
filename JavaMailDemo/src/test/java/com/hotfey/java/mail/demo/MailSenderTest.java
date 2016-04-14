package com.hotfey.java.mail.demo;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class MailSenderTest {
	@Ignore
	@Test
	public void testSender() throws Exception {
		String emailList = "to@email.com";

		String emailSubjectTxt = "Subject";
		String emailMsgTxt = "Content:" + new Date();

		String emailFromAddress = "from@email.com";

		String base = "smtp.email.com,25,true,from@email.com,password";

		String[] args = new String[5];
		args[0] = emailList;
		args[1] = emailSubjectTxt;
		args[2] = emailMsgTxt;
		args[3] = emailFromAddress;
		args[4] = base;

		MailSender.main(args);
	}
}
