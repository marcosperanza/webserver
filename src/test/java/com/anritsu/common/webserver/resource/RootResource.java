package com.anritsu.common.webserver.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

  @Produces("application/json")
  @Consumes("application/json")
  public interface RootResource {
    @GET
    Response get();

    @GET
    @Path("/runtime-error")
    Response runtimeError();

    @GET
    @Path("/badrequest-error")
    Response badRequestError();

    @POST
    Response create(SubmitBody body);
  }
