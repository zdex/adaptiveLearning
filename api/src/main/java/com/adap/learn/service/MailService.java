package com.adap.learn.service;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service 
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    public void sendOtp(String to, String otp) {
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Your Verification OTP");
        msg.setText("Your OTP is: " + otp + " (valid for 10 minutes)");
        mailSender.send(msg);
    }
}
