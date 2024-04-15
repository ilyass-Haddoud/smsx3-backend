package com.jwt.api.supplier;

import com.jwt.api.claim.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService userService) {
        this.supplierService = userService;
    }

    @GetMapping
    public List<Supplier> getSuppliers()
    {
        return this.supplierService.getSuppliers();
    }
}
