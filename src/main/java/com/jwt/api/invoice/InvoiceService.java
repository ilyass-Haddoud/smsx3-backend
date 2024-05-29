package com.jwt.api.invoice;

import com.jwt.api.config.ApiClient;
import com.jwt.api.config.SoapResponseParser;
import com.jwt.api.item.Item;
import com.jwt.api.item.ItemRepository;
import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SupplierRepository supplierRepository;
    private final WebClient webClient;
    private final SoapResponseParser soapResponseParser;
    private final ItemRepository itemRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, SupplierRepository supplierRepository, ApiClient apiClient, WebClient webClient, SoapResponseParser soapResponseParser, ItemRepository itemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.supplierRepository = supplierRepository;
        this.webClient = webClient;
        this.soapResponseParser = soapResponseParser;
        this.itemRepository = itemRepository;
    }

    public List<Invoice> getInvoicesBySupplier(Integer supplier_id)
    {
        try {
            Supplier supplier = this.supplierRepository.findById(supplier_id).orElseThrow(()->new RuntimeException("Supplier not found"));
            return supplier.getInvoices();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Invoice getInvoicesById(Integer id)
    {
        return this.invoiceRepository.findById(id).orElseThrow(()->new RuntimeException("Invoice not found"));
    }

    public List<Invoice> getAllInvoices()
    {
        return this.invoiceRepository.findAll();
    }

    public Invoice createInvoice(Integer supplierId, MultipartFile file, Invoice invoice) {
        try {
            Supplier supplier = this.supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            invoice.setSupplier(supplier);

            // Generate the filename
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFilename = supplier.getBpsnum() + "_" + timestamp + fileExtension;
            System.out.println(newFilename);

            // Define the relative folder path
            String folder = "storage/invoices";
            Path folderPath = Paths.get(folder);

            // Create the folder if it does not exist
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // Resolve the file path
            Path filePath = folderPath.resolve(newFilename);
            Files.write(filePath, file.getBytes());

            // Update the invoice document field with the new filename
            invoice.setDocument(newFilename);


            // Set the invoice for each item before saving the invoice
            if (invoice.getItems() != null) {
                for (Item item : invoice.getItems()) {
                    item.setInvoice(invoice);
                }
            }

            // Save the invoice, which will cascade and save the items as well
            return this.invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Resource loadInvoiceFile(String filename) throws IOException {
        Path filePath = Paths.get("storage/invoices").resolve(filename).normalize();
        if (Files.exists(filePath) && Files.isReadable(filePath)) {
            return new UrlResource(filePath.toUri());
        } else {
            throw new IOException("File not found or not readable: " + filename);
        }
    }

    public Invoice updateInvoiceStatus(Integer invoice_id,Integer status)
    {
        System.out.println("in updateInvoiceStatus method");
        System.out.println(invoice_id);
        System.out.println(status);

        Invoice existingInvoice = invoiceRepository.findById(invoice_id).orElseThrow(()->new RuntimeException("Invoice not found"));
        existingInvoice.setEtat(status);
        return this.invoiceRepository.save(existingInvoice);
    }

    public Invoice updateInvoice(Integer invoiceId, Invoice updatedInvoice) {
        Invoice existingInvoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        updateInvoiceFields(existingInvoice, updatedInvoice);
        setInvoiceForItems(existingInvoice);

        return invoiceRepository.save(existingInvoice);
    }

    private void updateInvoiceFields(Invoice existingInvoice, Invoice updatedInvoice) {
        existingInvoice.setSite(updatedInvoice.getSite());
        existingInvoice.setTypeFacture(updatedInvoice.getTypeFacture());
        existingInvoice.setNumeroPiece(updatedInvoice.getNumeroPiece());
        existingInvoice.setDateComptable(updatedInvoice.getDateComptable());
        existingInvoice.setTiers(updatedInvoice.getTiers());
        existingInvoice.setCollectif(updatedInvoice.getCollectif());
        existingInvoice.setDevise(updatedInvoice.getDevise());
        existingInvoice.setBonAPayer(updatedInvoice.getBonAPayer());
        existingInvoice.setDocumentOrigine(updatedInvoice.getDocumentOrigine());
        existingInvoice.setDateOrigine(updatedInvoice.getDateOrigine());
        existingInvoice.setReferenceInterne(updatedInvoice.getReferenceInterne());
        existingInvoice.setCommentaires0(updatedInvoice.getCommentaires0());
        existingInvoice.setCommentaires1(updatedInvoice.getCommentaires1());
        existingInvoice.setCommentaires2(updatedInvoice.getCommentaires2());
        existingInvoice.setTotalHTLignes(updatedInvoice.getTotalHTLignes());
        existingInvoice.setTotalTaxes(updatedInvoice.getTotalTaxes());
        existingInvoice.setMontantTTC(updatedInvoice.getMontantTTC());
        existingInvoice.setEtat(updatedInvoice.getEtat());
        existingInvoice.setTexteEntete71(updatedInvoice.getTexteEntete71());
        existingInvoice.setTexteEntete72(updatedInvoice.getTexteEntete72());
        existingInvoice.setTextePied81(updatedInvoice.getTextePied81());
        existingInvoice.setTextePied82(updatedInvoice.getTextePied82());
        if (updatedInvoice.getItems() != null) {
            existingInvoice.setItems(updatedInvoice.getItems());
        }
    }

    private void setInvoiceForItems(Invoice invoice) {
        if (invoice.getItems() != null) {
            for (Item item : invoice.getItems()) {
                item.setInvoice(invoice);
            }
        }
    }

    public Mono<List<String>> addInvoiceToSage(String iFileContent) {
        System.out.println("inside add invoice to sage service");

        String soapMessage = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"http://www.adonix.com/WSS\">\n" +
                "  <soapenv:Header/>\n" +
                "  <soapenv:Body>\n" +
                "    <wss:run soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "      <callContext xsi:type=\"wss:CAdxCallContext\">\n" +
                "        <codeLang xsi:type=\"xsd:string\">FRA</codeLang>\n" +
                "        <poolAlias xsi:type=\"xsd:string\">FORMATION</poolAlias>\n" +
                "        <poolId xsi:type=\"xsd:string\"></poolId>\n" +
                "        <requestConfig xsi:type=\"xsd:string\">\n" +
                "          <![CDATA[adxwss.optreturn=JSON&adxwss.beautify=true&adxwss.trace.on=off]]>\n" +
                "        </requestConfig>\n" +
                "      </callContext>\n" +
                "      <publicName xsi:type=\"xsd:string\">AOWSIMPORT</publicName>\n" +
                "      <inputXml xsi:type=\"xsd:string\">\n" +
                "        <![CDATA[{\n" +
                "          \"GRP1\": {\n" +
                "            \"I_MODIMP\": \"PIH\",\n" +
                "            \"I_AOWSTA\": \"NO\",\n" +
                "            \"I_EXEC\": \"REALTIME\",\n" +
                "            \"I_RECORDSEP\": \"|\",\n" +
                "            \"I_FILE\":\"" + iFileContent + "\"\n" +
                "          }\n" +
                "        }]]>\n" +
                "      </inputXml>\n" +
                "    </wss:run>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        return webClient.post()
                .uri("")
                .header("Authorization", basicAuthHeader())
                .header("SOAPAction","SOAPAction")
                .body(BodyInserters.fromValue(soapMessage))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseXml -> {
                    List<String> messages = soapResponseParser.extractAllMessages(responseXml);
                    return Mono.just(messages);
                });
    }


    public Mono<String> callSoapService(String bprValue) {
        System.out.println("bpr: "+bprValue);
        String soapMessage = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"http://www.adonix.com/WSS\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "    <wss:run soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "        <callContext xsi:type=\"wss:CAdxCallContext\">\n" +
                "        <codeLang xsi:type=\"xsd:string\">FRA</codeLang>\n" +
                "        <poolAlias xsi:type=\"xsd:string\">FORMATION</poolAlias>\n" +
                "        <poolId xsi:type=\"xsd:string\"/>\n" +
                "        <requestConfig xsi:type=\"xsd:string\">\n" +
                "            <![CDATA[adxwss.optreturn=JSON&adxwss.beautify=true&adxwss.trace.on=off]]>\n" +
                "        </requestConfig>\n" +
                "        </callContext>\n" +
                "        <publicName xsi:type=\"xsd:string\">AOWSEXPORT</publicName>\n" +
                "        <inputXml xsi:type=\"xsd:string\">\n" +
                "        <![CDATA[{\n" +
                "            \"GRP1\": {\n" +
                "            \"I_MODEXP\": \"PIH\",\n" +
                "            \"I_CHRONO\": \"NO\"\n" +
                "            },\n" +
                "            \"GRP2\": [\n" +
                "            {\n" +
                "                \"I_TCRITERE\": \"BPR='" + bprValue + "'\"\n" +
                "            }  \n" +
                "            ],\n" +
                "            \"GRP3\": {\n" +
                "            \"I_EXEC\": \"REALTIME\",\n" +
                "            \"I_RECORDSEP\": \"|\"\n" +
                "            }\n" +
                "        }]]>\n" +
                "        </inputXml>\n" +
                "    </wss:run>\n" +
                "    </soapenv:Body>\n" +
                "    </soapenv:Envelope>";

        return webClient.post()
                .uri("")
                .header("Authorization", basicAuthHeader())
                .header("SOAPAction","SOAPAction")
                .body(BodyInserters.fromValue(soapMessage))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseXml -> {
                    String oFile = soapResponseParser.extractOFile(responseXml);
                    return Mono.just(oFile);
                });
    }

    private String basicAuthHeader() {
        String username = "admin";
        String password = "admin";
        return "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
