package org.grupo12.services.implementation;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.grupo12.services.interfaces.IEmailService;

import java.util.Properties;

public class EmailService implements IEmailService {
    private final String host;
    private final String port;
    private final String username;
    private final String password;

    public EmailService(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Correo electrónico enviado");
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo electrónico");
            throw new RuntimeException(e);
        }
    }
}
