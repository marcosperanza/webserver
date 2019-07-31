package com.anritsu.common.webserver.mapper;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Default exception mapper. to regiter it add this in your web.xml
 *
 * <context-param>
 *         <param-name>resteasy.providers</param-name>
 *         <param-value>com.anritsu.common.webserver.mapper.UncaughtExceptionMapper</param-value>
 * 	</context-param>
 */

@Provider
public class UncaughtExceptionMapper implements ExceptionMapper<Throwable> {

    private final boolean sendExceptionDetails;

    public UncaughtExceptionMapper() {
        super();
        sendExceptionDetails = Boolean.parseBoolean(System.getProperty("owasp.map.exception.detail.enabled", "false"));
    }

    @Override
    public Response toResponse(Throwable ex) {

        String errorType = ex.getClass().getSimpleName();

        String message;
        if (sendExceptionDetails) {
            message = ex.getMessage();
        } else {
            message = "Your client has issued a malformed or illegal request. That’s all we know.";
        }
        ErrorMessage errorMessage = new ErrorMessage(setHttpStatus(ex), errorType, message);
        return Response.status(setHttpStatus(ex))
                        .entity(errorMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
    }

    private int setHttpStatus(Throwable ex) {
        if(ex instanceof WebApplicationException) {
            return ((WebApplicationException)ex).getResponse().getStatus();
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(); //defaults BAD REQUEST
        }
    }
}