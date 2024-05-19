package com.jwt.api.supplier;

import com.jwt.api.PassworResetToken.PasswordResetToken;
import com.jwt.api.PassworResetToken.PasswordResetTokenService;
import com.jwt.api.claim.Claim;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public SupplierController(SupplierService userService, PasswordEncoder passwordEncoder, PasswordResetTokenService passwordResetTokenService) {
        this.supplierService = userService;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @CrossOrigin
    @GetMapping
    public List<Supplier> getSuppliers()
    {
        return this.supplierService.getSuppliers();
    }

    @CrossOrigin
    @GetMapping("getInfo/{supplier_email}")
    public Supplier getSupplier(@PathVariable String supplier_email)
    {
        return this.supplierService.getSupplierByEmail(supplier_email);
    }

    @CrossOrigin
    @PostMapping("disable")
    public Supplier disableSupplier(@RequestBody SupplierDisableDTO supplier)
    {
        return this.supplierService.updateSupplier(supplier.getBpsaddeml(),supplier.isDisactivated());
    }

    @CrossOrigin
    @PostMapping("change_password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) throws MessagingException {
        return this.supplierService.changePassword(passwordChangeDTO.getEmail(),passwordChangeDTO.getCurrentPassword(),passwordChangeDTO.getNewPassword());
    }

    @CrossOrigin
    @GetMapping("forgot_password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email)
    {
        System.out.println(email);
        return this.passwordResetTokenService.forgotPassword(email);
    }

    @CrossOrigin
    @PostMapping("reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword)
    {
        return this.passwordResetTokenService.resetPassword(token,newPassword);
    }
}
