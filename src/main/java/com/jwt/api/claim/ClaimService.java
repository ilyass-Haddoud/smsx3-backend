package com.jwt.api.claim;

import com.jwt.api.email.Email;
import com.jwt.api.email.EmailService;
import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListResourceBundle;

@Service
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final SupplierService supplierService;
    private final EmailService emailService;

    @Autowired
    public ClaimService(ClaimRepository claimRepository, SupplierService supplierService, EmailService emailService) {
        this.claimRepository = claimRepository;
        this.supplierService = supplierService;
        this.emailService = emailService;
    }

    public List<Claim> getAllClaims()
    {
        return claimRepository.findAll();
    }

    public ResponseEntity<String> addClaim(String entete,String message,Integer supplier_id)
    {
        try {
            Supplier supplier = this.supplierService.getSupplierById(supplier_id);
            Claim claim = new Claim(entete,message,supplier);
            System.out.println("claim added: "+claim);
//            Email email = new Email();
//            email.setSubject(entete);
//            email.setMessageBody(message);
//            this.emailService.sendEmail(email);
            claimRepository.save(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body("success");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public List<Claim> getSupplierClaims(Integer supplier_id)
    {
        Supplier supplier = this.supplierService.getSupplierById(supplier_id);
        return supplier.getClaims();
    }
}
