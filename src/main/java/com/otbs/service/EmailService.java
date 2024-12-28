package com.otbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    
    public void sendEmail(String toEmail, String subject, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println("sender email id : "+toEmail);
        System.out.println("subject sender email id : "+subject);
        System.out.println("message sender email id : "+messageText); 
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(messageText);
        mailSender.send(message);
    }

    public void sendThankYouEmail(String toEmail, String customerName) {
        // Create the email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thank You for Registering!");
        message.setText("Dear " + customerName + ",\n\nThank you for registering with us! We are excited to have you on board.\n\nBest Regards,\nBharat Teleservices");
        message.setFrom("your_email@example.com"); // Replace with your email

        // Send the email
        mailSender.send(message);
    }
}
