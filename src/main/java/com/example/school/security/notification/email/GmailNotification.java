package com.example.school.security.notification.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.school.security.notification.INotification;


@Service
public class GmailNotification implements INotification{

    private final static Logger LOGGER = LoggerFactory.getLogger(GmailNotification.class);

    private final JavaMailSender gmailMailSender;

    @Autowired
    public GmailNotification(final JavaMailSender gmailMailSender){
        this.gmailMailSender = gmailMailSender;
    }

    @Override
    @Async
    public void send(String to,String subject,String message) {
        try {
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setFrom("abdelrhmansasalh@gmail.comemail");
            smm.setTo(to);
            smm.setSubject(subject);
            smm.setText(message);
            gmailMailSender.send(smm);
        } catch (Exception e) {
            LOGGER.error("Faild to send email");
            throw new IllegalStateException("faild to send email");
        }
    }

    @Override
    public void sendVerification(String toEmail, String verifCode) {
        try {
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setFrom("abdelrhmansasalh@gmail.com");
            smm.setTo(toEmail);
            smm.setSubject("Email Verification Code");
            smm.setText("Your verification code is _!_ : " + verifCode);
            System.out.println(smm);
            gmailMailSender.send(smm);
        } catch (Exception e) {
            LOGGER.error("Faild to send email");
            throw new IllegalStateException(e.getMessage());
        }
    }

}
