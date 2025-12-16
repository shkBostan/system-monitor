package com.shkbostan.system_monitor.service;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */

@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    private final Environment env;

    public EmailNotificationService(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.env = env;
    }

    public void send(String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(env.getProperty("notification.email.to"));
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}

