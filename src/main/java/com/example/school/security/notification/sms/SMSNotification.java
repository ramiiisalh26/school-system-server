package com.example.school.security.notification.sms;

import org.springframework.stereotype.Service;

import com.example.school.security.notification.INotification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SMSNotification implements INotification {
    
    @Override
    public void send(String to, String subject, String message) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendVerification(String toEmail, String verifyCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendVerification'");
    }

}
