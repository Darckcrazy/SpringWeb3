package com.example.SpringWeb3.u5d7hw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAuthorConfirmationEmail(String email, String name, String surname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to Our Blogging Platform!");
        message.setText(String.format(
            "Hello %s %s,\n\n" +
            "Welcome to our blogging platform! Your author account has been successfully created.\n\n" +
            "You can now start writing and publishing your blog posts.\n\n" +
            "Best regards,\n" +
            "The Blogging Team",
            name, surname
        ));
        
        mailSender.send(message);
    }
}
