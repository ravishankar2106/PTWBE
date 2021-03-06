package com.bind.ptw.be.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.env.Environment;

public class EmailUtil {

	public static void sendEmail(EmailContent emailContest, MailConfiguration mailConfig) throws Exception {

		// Create a Properties object to contain connection configuration
		// information.
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", mailConfig.getPort());
		Transport transport = null;
		// Send the message.
		try {
			// Set properties indicating that we want to use STARTTLS to encrypt
			// the connection.
			// The SMTP session will begin on an unencrypted connection, and
			// then the client
			// will issue a STARTTLS command to upgrade to an encrypted
			// connection.
			props.put("mail.smtp.auth", "true");
			
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.starttls.required", "true");

			// Create a Session object to represent a mail session with the
			// specified properties.
			Session session = Session.getDefaultInstance(props);

			// Create a message with the specified information.
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mailConfig.getFromAddress()));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					emailContest.getToAddress()));
			msg.setSubject(emailContest.getEmailSubject());
			msg.setContent(emailContest.getEmailBody(), "text/plain");

			// Create a transport.
			transport = session.getTransport();

			// Connect to Amazon SES using the SMTP username and password you
			// specified above.
			transport.connect(mailConfig.getHost(), mailConfig.getSmtpUserName(), mailConfig.getSmtpPassword());

			// Send the email.
			transport.sendMessage(msg, msg.getAllRecipients());
		} finally {
			// Close and terminate the connection.
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static MailConfiguration getMailConfiguration(Environment env){
		MailConfiguration config = new MailConfiguration();
		String senderAddress = env.getProperty(DBConstants.FROM_ADDRESS_KEY);
		String smtpUserName = env.getProperty(DBConstants.SMTP_USERNAME_KEY);
		String smtpPassword = env.getProperty(DBConstants.SMTP_PASSWORD_KEY);
		String smtpHost = env.getProperty(DBConstants.SMTP_HOST_KEY);
		String smtpPort = env.getProperty(DBConstants.PORT_KEY);
		
		config.setFromAddress(senderAddress);
		config.setSmtpUserName(smtpUserName);
		config.setSmtpPassword(smtpPassword);
		config.setHost(smtpHost);
		try{
			config.setPort(Integer.parseInt(smtpPort));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return config;
		
	}

}
