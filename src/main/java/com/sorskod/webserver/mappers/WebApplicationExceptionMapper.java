package com.sorskod.webserver.mappers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Aleksandar Babic
 */
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException>, ErrorableResponse, Feature {

  @Override
  public Response toResponse(WebApplicationException exception) {
    return buildResponse(Response.Status.fromStatusCode(exception.getResponse().getStatus()), exception.getMessage());
  }

  @Override
  public boolean configure(FeatureContext context) {
    context.register(this.getClass());
    return true;
  }
}
