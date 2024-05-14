package com.jwt.api.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

@Component
public class SoapResponseParser {
    public String extractOFile(String responseXml) {
        try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML string into a Document object
            ByteArrayInputStream input = new ByteArrayInputStream(responseXml.getBytes());
            Document doc = builder.parse(input);

            // Get the value of O_FILE
            NodeList resultXmlList = doc.getElementsByTagName("resultXml");
            if (resultXmlList.getLength() > 0) {
                Element resultXml = (Element) resultXmlList.item(0);
                String resultXmlContent = resultXml.getTextContent();
                // Now parse this JSON content to extract O_FILE
                return extractOFileFromJson(resultXmlContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractOFileFromJson(String jsonContent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode oFileNode = rootNode.path("GRP3").path("O_FILE");
            return oFileNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
