package com.jwt.api.invoice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public Invoice addInvoice(@PathVariable Integer supplier_id, @RequestParam("file") MultipartFile file, @RequestParam("invoice") String invoice) throws JsonProcessingException {
        Invoice invoiceObj = new ObjectMapper().readValue(invoice, Invoice.class);
        return this.invoiceService.createInvoice(supplier_id, file, invoiceObj);
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
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable String filename) throws IOException {
        try{
            Resource resource = invoiceService.loadInvoiceFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @CrossOrigin
    @PutMapping("/{invoice_id}")
    public Invoice updateInvoice(@PathVariable Integer invoice_id,@RequestBody Invoice invoice)
    {
        return this.invoiceService.updateInvoice(invoice_id,invoice);
    }

    @CrossOrigin
    @PutMapping("/syncStatus/{invoice_id}/{numeroPiece}")
    public Invoice syncStatus(@PathVariable Integer invoice_id, @PathVariable String numeroPiece, @RequestBody Map<String, Integer> status)
    {
        System.out.println("in syncStatus controller");
        System.out.println(invoice_id);
        System.out.println(status);
        return this.invoiceService.updateInvoiceStatus(invoice_id, status.get("etat"), numeroPiece);
    }

    @CrossOrigin
    @PostMapping("/sage/addInvoice")
    public Mono<List<String>> addInvoice(@RequestBody String invoice)
    {
        System.out.println("inside add invoice to sage controller");
        System.out.println(invoice);
        return this.invoiceService.addInvoiceToSage(invoice);
    }

    @CrossOrigin
    @PostMapping("/call/{bpr}")
    public Mono<String> callSoapService(@PathVariable String bpr) {
        return invoiceService.callSoapService(bpr);
    }
}
