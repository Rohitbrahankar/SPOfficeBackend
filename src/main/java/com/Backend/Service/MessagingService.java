package com.Backend.Service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MessagingService {

    public boolean sendEmail(String to, String from, String msg, String subject) throws MessagingException {
        boolean result = false;
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp,starttls.enable", true);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        String email = "aadityathakare124@gmail.com";
        String password = "wxin ugvk sthc rhmw";
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setContent(msg, "text/html; charset=utf-8");

            Transport.send(message);
            result = true;
        } catch (Exception E) {
            E.printStackTrace();
        }

        return result;

    }


    

}
