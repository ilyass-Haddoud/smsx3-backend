package com.jwt.api.invoice;

import com.jwt.api.config.ApiClient;
import com.jwt.api.config.SoapResponseParser;
import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SupplierRepository supplierRepository;
    private final WebClient webClient;
    private final SoapResponseParser soapResponseParser;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, SupplierRepository supplierRepository, ApiClient apiClient, WebClient webClient, SoapResponseParser soapResponseParser) {
        this.invoiceRepository = invoiceRepository;
        this.supplierRepository = supplierRepository;
        this.webClient = webClient;
        this.soapResponseParser = soapResponseParser;
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

    public Mono<String> callSoapService() {
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
                "            \"I_MODEXP\": \"SUP\",\n" +
                "            \"I_CHRONO\": \"NO\"\n" +
                "            },\n" +
                "            \"GRP2\": [\n" +
                "            {\n" +
                "                \"I_TCRITERE\": \"BPR='AE020'\"\n" +
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
