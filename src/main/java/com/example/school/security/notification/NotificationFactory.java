package com.example.school.security.notification;

import org.springframework.stereotype.Component;

import com.example.school.security.notification.email.GmailNotification;
import com.example.school.security.notification.email.OutlookNotification;
import com.example.school.security.notification.sms.SMSNotification;

@Component
public class NotificationFactory {
    
    private final GmailNotification gmailNotification;
    private final SMSNotification smsNotification;
    private final OutlookNotification outlookNotification;

    public NotificationFactory(
        final GmailNotification gmailNotification,
        final SMSNotification smsNotification,
        final OutlookNotification outlookNotification){
        this.gmailNotification = gmailNotification;
        this.smsNotification = smsNotification;
        this.outlookNotification = outlookNotification;
    }

    public INotification createNotification(String type){
        return switch (type.toUpperCase()) {
            case "GMAIL" -> gmailNotification;
            case "OUTLOOK" -> outlookNotification;
            case "SMS" -> smsNotification;
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
}
