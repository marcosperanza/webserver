package com.anritsu.common.webserver.resource;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Singleton

  public class RootResourceImpl implements RootResource {

    private final MessengerService service;

    @Inject
    public RootResourceImpl(MessengerService service) {
      this.service = service;
    }

    public Response get() {
      return Response.ok(Collections.singletonMap("message", service.getMessage())).build();
    }

    @Override
    public Response runtimeError() {
      throw new RuntimeException("Some random error.");
    }

    @Override
    public Response badRequestError() {
      throw new BadRequestException("Some random BadRequestException.");
    }

    @Override
    public Response create(SubmitBody body) {
      return Response.accepted().entity(body).build();
    }
  }