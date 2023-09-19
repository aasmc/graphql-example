package ru.aasmc.graphql.server.error;


import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {


    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof GraphQLServerError) {
            GraphQLServerError err = (GraphQLServerError) ex;
            int code = err.getCode();
            ErrorType errorType = errorType(code);
            return GraphqlErrorBuilder.newError()
                    .errorType(errorType)
                    .message(err.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        return null;
    }


    private ErrorType errorType(int code) {
        return switch (code) {
            case 404 -> ErrorType.NOT_FOUND;
            case 400 -> ErrorType.BAD_REQUEST;
            default -> ErrorType.INTERNAL_ERROR;
        };
    }
}
