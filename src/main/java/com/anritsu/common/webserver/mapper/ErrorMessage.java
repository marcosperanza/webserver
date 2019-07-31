package com.anritsu.common.webserver.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

    private final int status;
    private final String errorType;
    private final String message;

    @JsonCreator
    public ErrorMessage(@JsonProperty("status") int status,
                        @JsonProperty("errorType") String errorType,
                        @JsonProperty("message") String message) {
        this.status = status;
        this.errorType = errorType;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }
}
