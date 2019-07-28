/*
 * : WebAppWarIntegrationTest.java 29021 2013-04-10 15:47:16Z masp $
 *
 * Copyright 1998-2006 by Anritsu A/S,
 * Kirkebjerg Alle 86, DK-2605 Broendby, Denmark.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Anritsu A/S.
 */
package com.sorskod.webserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sorskod.webserver.entities.Error;
import com.sorskod.webserver.module.DefaultFeatureModule;
import com.sorskod.webserver.providers.WarServletContextHandlerProvider;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Marco Speranza
 * @version : $
 */
public class WebAppWarIntegrationTest {


    private final static Logger LOGGER = LoggerFactory.getLogger(WebAppWarIntegrationTest.class);

    Injector injector;
    Server server;
    Client client;

    @Before
    public void initialize() throws Exception {

        //System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        //System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");
        injector = Guice.createInjector(new WarTestModule(), new DefaultFeatureModule(), new WebServerModule(){
            @Override
            protected Class<? extends Provider<Handler>> getHandlerProviderClass() {
                return WarServletContextHandlerProvider.class;
            }
        });

        client = ClientBuilder.newClient();
        server = injector.getInstance(Server.class);
        server.start();
    }

    @After
    public  void tearDown() throws Exception {
        injector.getInstance(Server.class).stop();
    }


    @Test
    public void checkThatTheRootContextIsAWebPageProvidedByWar() throws InterruptedException {
        WebTarget webTarget = client.target(server.getURI().toString());

        Invocation.Builder invocationBuilder
                = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();


        assertThat(response.getStatus(), equalTo(OK.getStatusCode()));
        assertThat(response.getStatus(), equalTo(OK.getStatusCode()));
        List<Object> objects = response.getHeaders().get("Content-Type");
        assertTrue(objects.contains("text/html"));
    }

    @Test
    public void checkThatTheResurcesAreBindInTheResourceContextRoot() throws InterruptedException {
        WebTarget webTarget = client.target(server.getURI().toString());
        Invocation.Builder invocationBuilder = webTarget.path("/api")
                .request(MediaType.APPLICATION_JSON);


        Response response = invocationBuilder.get();


        assertThat(response.getStatus(), equalTo(OK.getStatusCode()));
        List<Object> objects = response.getHeaders().get("Content-Type");
        assertTrue(objects.contains(MediaType.APPLICATION_JSON));
        Map<String, String> entity = response.readEntity(new GenericType<Map<String, String>>() {});

        assertThat(entity.get("message"), equalTo("Hello."));

    }

    @Test
    public void checkResourcesErrorHandler() throws InterruptedException {
        WebTarget webTarget = client.target(server.getURI().toString());
        Invocation.Builder invocationBuilder = webTarget.path("/api/badrequest-error")
                .request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();

        Map<String, Error> entity = response.readEntity(new GenericType<Map<String, Error>>() {});

        assertThat(response.getStatus(), equalTo(BAD_REQUEST.getStatusCode()));
        assertThat(entity.get("error").getCode(), equalTo(BAD_REQUEST.getStatusCode()));
        assertThat(entity.get("error").getMessage(), notNullValue());

    }

}


