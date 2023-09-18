package ru.aasmc.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().baseUrl("http://localhost:9090/graphql");
    }

    @Bean
    public HttpGraphQlClient graphQlClient(WebClient.Builder webClient) {
        return HttpGraphQlClient.builder(webClient.build())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
