package com.jwt.api.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("{supplier_id}/addInvoice")
    public Invoice addInvoice(@PathVariable Integer supplier_id,@RequestBody Invoice invoice)
    {
        return this.invoiceService.createInvoice(supplier_id,invoice);
    }

    @GetMapping("/{supplier_id}")
    public List<Invoice> getAllInvoices(@PathVariable Integer supplier_id)
    {
        return this.invoiceService.getAllInvoices(supplier_id);
    }
}
