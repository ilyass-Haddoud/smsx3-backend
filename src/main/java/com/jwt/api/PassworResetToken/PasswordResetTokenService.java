package com.jwt.api.PassworResetToken;

import com.jwt.api.email.Email;
import com.jwt.api.email.EmailService;
import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierRepository;
import com.jwt.api.supplier.SupplierService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;
    private final SupplierService supplierService;

    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService, SupplierRepository supplierRepository, PasswordEncoder passwordEncoder, SupplierService supplierService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.supplierRepository = supplierRepository;
        this.passwordEncoder = passwordEncoder;
        this.supplierService = supplierService;
    }

    public void createPasswordResetTokenForUser(Supplier supplier, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setSupplier(supplier);
        myToken.setExpiryDate(calculateExpiryDate(24 * 60));
        passwordResetTokenRepository.save(myToken);
    }

    public void sendPasswordResetEmail(Supplier supplier, String token) throws MessagingException {
        String recipientAddress = supplier.getBpsaddeml();
        String subject = "Réinitialisation de mot de passe";
        String resetPasswordUrl = "http://localhost:5173/reset_password?token=" + token;
        String message = "Cher(e) " + supplier.getBpsfname() + " " + supplier.getBpslname() + ",\n\n"
                + "Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant : \n"
                + resetPasswordUrl + "\n\n"
                + "Cordialement,\n\nSage InvoiceHub";

        Email mail = new Email();
        mail.setRecipient(recipientAddress);
        mail.setSubject(subject);
        mail.setMessageBody(message);
        emailService.sendEmail(mail);
    }

    public ResponseEntity<String> resetPassword(String token,String newPassword)
    {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }

        Supplier supplier = passwordResetToken.getSupplier();
        supplier.setBpspasse(passwordEncoder.encode(newPassword));
        supplierRepository.save(supplier);

        passwordResetTokenRepository.delete(passwordResetToken);

        return ResponseEntity.ok("Password reset successfully");
    }

    public ResponseEntity<String> forgotPassword(String email)
    {
        Supplier supplier = supplierService.getSupplierByEmail(email);
        if (supplier == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(supplier, token);

        try {
            sendPasswordResetEmail(supplier, token);
            return ResponseEntity.ok("Password reset email sent");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTimeInMillis());
    }
}

