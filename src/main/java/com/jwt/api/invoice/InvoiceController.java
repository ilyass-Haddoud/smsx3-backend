package com.jwt.api.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @CrossOrigin
    @PostMapping("{supplier_id}/addInvoice")
    public Invoice addInvoice(@PathVariable Integer supplier_id,@RequestBody Invoice invoice)
    {
        System.out.println("Adding invoice route called");
        System.out.println(invoice.getBonAPayer());
        return this.invoiceService.createInvoice(supplier_id,invoice);
    }

    @CrossOrigin
    @GetMapping
    public List<Invoice> getAllInvoices()
    {
        return this.invoiceService.getAllInvoices();
    }

    @CrossOrigin
    @GetMapping("/{supplier_id}")
    public List<Invoice> getInvoicesBySupplier(@PathVariable Integer supplier_id)
    {
        return this.invoiceService.getInvoicesBySupplier(supplier_id);
    }

    @CrossOrigin
    @GetMapping("/id/{id}")
    public Invoice getInvoiceById(@PathVariable Integer id)
    {
        return this.invoiceService.getInvoicesById(id);
    }

    @CrossOrigin
    @PutMapping("/{invoice_id}")
    public Invoice updateInvoice(@PathVariable Integer invoice_id,@RequestBody Invoice invoice)
    {
        return this.invoiceService.updateInvoice(invoice_id,invoice);
    }

    @CrossOrigin
    @PostMapping("sage/addInvoice")
    public Mono<List<String>> addInvoice(@RequestBody String invoice)
    {
        System.out.println("inside add invoice to sage controller");
        System.out.println(invoice);
        return this.invoiceService.addInvoiceToSage(invoice);
    }

    @CrossOrigin
    @PostMapping("/call")
    public Mono<String> callSoapService() {
        return invoiceService.callSoapService();
    }
}
