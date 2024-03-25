package com.jwt.api.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
