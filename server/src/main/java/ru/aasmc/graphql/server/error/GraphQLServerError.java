package ru.aasmc.graphql.server.error;

import lombok.Getter;

@Getter
public class GraphQLServerError extends RuntimeException {
    private final int code;


    public GraphQLServerError(String message, int code) {
        super(message);
        this.code = code;
    }
}
