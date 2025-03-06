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
        switch (type.toUpperCase()) {
            case "GMAIL":
                return gmailNotification;
            case "OUTLOOK":
                return outlookNotification;
            case "SMS":
                return smsNotification;
            default:
                throw new IllegalArgumentException("Unkown notification type: " + type);
        }
    }
}
