package com.example.school.security.notification;


public interface INotification{

    void send(String to,String subject,String message);
    
    void sendVerification(String toEmail, String verifCode);
}
