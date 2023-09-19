package ru.aasmc.client.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.client.ClientGraphQlRequest;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.GraphQlClientInterceptor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClientInterceptor implements GraphQlClientInterceptor {

    @Override
    public Mono<ClientGraphQlResponse> intercept(ClientGraphQlRequest request, Chain chain) {
        return chain.next(request)
                .doOnError(e -> log.error("Caught exception: {}", e.getMessage()))
                .handle((response, sink) -> {
                    if (isValidResponse(response)) {
                        sink.next(response);
                        return;
                    }
                    List<ResponseError> errors = response.getErrors();
                    String message = errors.stream().map(ResponseError::getMessage)
                            .collect(Collectors.joining("; "));
                    sink.error(new ClientException(message, 400));
                });
    }

    private boolean isValidResponse(ClientGraphQlResponse response) {
        return response.getData() != null && response.getErrors().isEmpty() && response.isValid();
    }
}
