package com.bind.ptw.be.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static final String FROM_ADDRESS = "ravishankar2106@gmail.com";
	public static final String SMTP_USERNAME = "AKIAJUIM6EUWQ2PNBFKQ";
	public static final String SMTP_PASSWORD = "AhsTBBggJN2wcx72bAQJjC7uA20WrC8U48X6+YJ/ZD9L";
	public static final String HOST = "email-smtp.us-west-2.amazonaws.com";
	public static final int PORT = 587;

	public static void sendEmail(EmailContent emailContest) throws PTWException {

		// Create a Properties object to contain connection configuration
		// information.
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
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
			msg.setFrom(new InternetAddress(FROM_ADDRESS));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					emailContest.getToAddress()));
			msg.setSubject(emailContest.getEmailSubject());
			msg.setContent(emailContest.getEmailBody(), "text/plain");

			// Create a transport.
			transport = session.getTransport();

			// Connect to Amazon SES using the SMTP username and password you
			// specified above.
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

			// Send the email.
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_EMAIL_DEL_FAILURE,PTWConstants.ERROR_DESC_EMAIL_DEL_FAILURE);
		} finally {
			// Close and terminate the connection.
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
