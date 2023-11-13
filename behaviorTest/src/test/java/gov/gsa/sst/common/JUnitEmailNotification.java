package gov.gsa.sst.common;

import javax.mail.*;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JUnitEmailNotification {

	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final int SMTP_PORT = 587;
	private static final String SMTP_USERNAME = "venus.hu@gsa.gov";
	private static final String SMTP_PASSWORD = "qiugvhlijvwcfmzh";

	private static final String SENDER_EMAIL = "venus.hu@gsa.gov";
	private static final String[] RECIPIENT_EMAIL = { "venus.hu@gsa.gov", "nuerzhati.yijiati@gsa.gov" };

	private static final String EMAIL_SUBJECT = "eOffer Test Execution Result";


	public static void sendEmailNotification(String emailContent) {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTP_HOST);
		properties.put("mail.smtp.port", SMTP_PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
			}
		});

		try {
			// Create a MimeMessage object
			Message message = new MimeMessage(session);

			// Set the sender and recipient addresses
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			for (String recipient : RECIPIENT_EMAIL) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			}

			// Set the email subject and content
			message.setSubject(EMAIL_SUBJECT);
			message.setText(emailContent);

			// Send the email
			Transport.send(message);

			System.out.println("Email notification sent successfully.");
		} catch (MessagingException e) {
			System.out.println("Failed to send email notification. Error: " + e.getMessage());
		}
	}

}
