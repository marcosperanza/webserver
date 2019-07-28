/*
 * : WarTestModule.java 29021 2013-04-10 15:47:16Z masp $
 *
 * Copyright 1998-2006 by Anritsu A/S,
 * Kirkebjerg Alle 86, DK-2605 Broendby, Denmark.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Anritsu A/S.
 */
package com.sorskod.webserver;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.MapBinder;
import com.sorskod.webserver.annotations.ContextHandlerAttributes;
import com.sorskod.webserver.annotations.ContextHandlerInitParams;
import com.sorskod.webserver.annotations.DefaultConnector;
import com.sorskod.webserver.annotations.SecureConnector;
import com.sorskod.webserver.connectors.https.HTTPSConnectorModule;
import com.sorskod.webserver.resource.RootResource;
import com.sorskod.webserver.resource.RootResourceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * TODO FILL ME!
 *
 * @author Marco Speranza
 * @version : $
 */
public class WarTestModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new HTTPSConnectorModule());
        bind(RootResource.class).to(RootResourceImpl.class).asEagerSingleton();

        MapBinder<String, String> mapbinder
                = MapBinder.newMapBinder(binder(), String.class, String.class, ContextHandlerInitParams.class);
        mapbinder.addBinding("org.eclipse.jetty.servlet.Default.dirAllowed").toInstance("false");

        MapBinder<String, String> mapbinder2
                = MapBinder.newMapBinder(binder(), String.class, String.class, ContextHandlerAttributes.class);
        mapbinder2.addBinding("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern").toInstance(".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );

    }



    @Provides
    WarContextConfigurator warContextConfigurator() {
        return new WarContextConfigurator() {
            @Override
            public String getWar() {
                return new File("src/test/resources/sample.war").getAbsolutePath();
            }

            @Override
            public File getTempDirectory() {
                try {
                    Path tempDirectory = Files.createTempDirectory("tmp-");
                    return tempDirectory.toFile();
                } catch (IOException e) {
                    throw  new RuntimeException(e);
                }

            }

            @Override
            public boolean getPersistTempDirectory() {
                return false;
            }
        };
    }

    @SecureConnector
    @Provides
    SslConfigurator configurator() {
        return new SslConfigurator() {
            @Override
            public String getKeystoreFile() {
                return "";
            }

            @Override
            public String getKeyStorePassword() {
                return "";
            }

            @Override
            public String getKeyManagerPassword() {
                return "";
            }

            @Override
            public String getTrustStorePath() {
                return "";
            }

            @Override
            public String getTrustStorePassword() {
                return "";
            }

            @Override
            public int getPort() {
                return 9112;
            }
        }; // random port
    }

    @SecureConnector
    @Provides
    Configurator c2() {
        return () -> 0;
    }

    @DefaultConnector
    @Provides
    Configurator c() {
        return () -> 0;
    }

}
