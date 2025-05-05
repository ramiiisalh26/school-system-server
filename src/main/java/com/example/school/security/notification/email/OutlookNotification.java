package com.example.school.security.notification.email;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.school.security.notification.INotification;

import jakarta.mail.internet.MimeMessage;

@Service
public class OutlookNotification implements INotification{

    private final static Logger LOGGER = LoggerFactory.getLogger(OutlookNotification.class);

    private final JavaMailSender outlookMailSender;

    @Autowired
    public OutlookNotification(final JavaMailSender outlookMailSender){
        this.outlookMailSender = outlookMailSender;
    }

    @Override
    public void send(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = outlookMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom("abdelrhmansasalh@outlook.com");
            outlookMailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.debug("Faild to send email");
            throw new IllegalStateException("Failed to send email via Outlook", e);
        }
    }

    @Override
    public void sendVerification(String toEmail, String verifyCode) {
        try {
            MimeMessage mimeMessage = outlookMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setTo(toEmail);
            helper.setSubject("Email Verification Code");
            helper.setText("Your verification code is _!_ : " + verifyCode);
            helper.setFrom("your-outlook-email@outlook.com");
            outlookMailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.debug("Faild to send email");
            throw new IllegalStateException("Failed to send email via Outlook", e);
        }
    }
    
}
