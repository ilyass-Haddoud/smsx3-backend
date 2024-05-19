package com.jwt.api.email;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mail")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @CrossOrigin
    @PostMapping("/send")
    public void SendMail(@RequestBody Email email) throws MessagingException {
        this.emailService.sendEmail(email);
    }
}
