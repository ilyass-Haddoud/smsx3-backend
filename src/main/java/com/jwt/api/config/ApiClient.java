package com.jwt.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiClient {
    @Bean
    public WebClient webclient() {
        return WebClient
            .builder()
            .baseUrl("http://192.168.88.172:8124/soap-generic/syracuse/collaboration/syracuse/CAdxWebServiceXmlCC")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
            .build();  }
}
