package ru.aasmc.graphql.server.error;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * WebGraphQlHandler can modify the ExecutionResult, for example,
 * to inspect and modify request validation errors that are raised
 * before execution begins and which cannot be handled with a DataFetcherExceptionResolver.
// */
@Slf4j
@Component
public class RequestErrorInterceptor implements WebGraphQlInterceptor {
    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        return chain.next(request).map(response -> {
                    if (response.isValid()) {
                        return response;
                    }
                    List<GraphQLError> errors = response.getErrors().stream()
                            .map(error -> GraphqlErrorBuilder.newError()
                                    .errorType(error.getErrorType())
                                    .path(error.getParsedPath())
                                    .extensions(error.getExtensions())
                                    .message(error.getMessage())
                                    .locations(error.getLocations())
                                    .build()).toList();
                    return response.transform(builder -> builder.errors(errors).build());
                })
                .log(log.getName());
    }
}
