package com.jwt.api.invoice;

import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, SupplierRepository supplierRepository) {
        this.invoiceRepository = invoiceRepository;
        this.supplierRepository = supplierRepository;
    }

    public Invoice createInvoice(Integer supplier_id,Invoice invoice)
    {
        try {
            Supplier supplier = this.supplierRepository.findById(supplier_id).orElseThrow(()->new RuntimeException("Supplier not found"));
            supplier.getInvoices().add(invoice);
            invoice.setSupplier(supplier);
            return this.invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Invoice> getAllInvoices(Integer supplier_id)
    {
        try {
            Supplier supplier = this.supplierRepository.findById(supplier_id).orElseThrow(()->new RuntimeException("Supplier not found"));
            return supplier.getInvoices();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
