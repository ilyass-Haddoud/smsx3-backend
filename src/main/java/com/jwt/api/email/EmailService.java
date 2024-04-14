package com.jwt.api.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Email emailDetails){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mailer = new MimeMessageHelper(message);

            mailer.setFrom(emailSender,"Sage Supplier Portal");
            mailer.setTo(emailDetails.getRecipient());
            mailer.setText(emailDetails.getMessageBody());
            mailer.setSubject(emailDetails.getSubject());

            javaMailSender.send(message);
            System.out.println("Mail sent successfully");
        }catch (Exception exception){
            System.out.println("Failure occurred while sending email");
        }
    }
}
