package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.api.exceptions.ServiceErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {

    private final WebClient coreServiceWebClient;

    public Optional<ProductDto> findById(Long id) {

        ProductDto productDto = coreServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/products/{id}").build(id))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new ServiceErrorException("Core service error")))
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(new ResourceNotFoundException("Product not found")))
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);
    }

}
