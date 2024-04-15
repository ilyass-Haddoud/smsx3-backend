package com.jwt.api.claim;

import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final SupplierService supplierService;

    @Autowired
    public ClaimService(ClaimRepository claimRepository, SupplierService supplierService) {
        this.claimRepository = claimRepository;
        this.supplierService = supplierService;
    }

    public ResponseEntity<String> addClaim(String entete,String message,Integer supplier_id)
    {
        try {
            Supplier supplier = this.supplierService.getSupplierById(supplier_id);
            Claim claim = new Claim(entete,message,supplier);
            claimRepository.save(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body("success");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
