package com.anritsu.common.webserver;

import com.anritsu.common.webserver.annotations.BaseConfiguration;
import com.anritsu.common.webserver.connectors.ConnectorFactory;
import com.anritsu.common.webserver.providers.HttpConfigurationProvider;
import com.anritsu.common.webserver.providers.JettyServerProvider;
import com.anritsu.common.webserver.providers.WarServletContextHandlerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;

import javax.inject.Provider;
import java.util.Set;

/**
 * @author Aleksandar Babic
 */
public class WebServerModule extends AbstractModule  {

  protected void configure() {
    install(createServletModule());

    requireBinding(Key.get(new TypeLiteral<Set<ConnectorFactory>>() {}));

    bind(Handler.class)
        .toProvider(getHandlerProviderClass())
        .asEagerSingleton();

    bind(Server.class)
        .toProvider(getJettyServerProviderClass())
        .asEagerSingleton();

    bind(HttpConfiguration.class)
        .annotatedWith(BaseConfiguration.class)
        .toProvider(getHttpConfigurationProviderClass())
        .asEagerSingleton();

  }

  protected Class<HttpConfigurationProvider> getHttpConfigurationProviderClass() {
    return HttpConfigurationProvider.class;
  }

  protected Class<JettyServerProvider> getJettyServerProviderClass() {
    return JettyServerProvider.class;
  }

  protected Class<? extends Provider<Handler>> getHandlerProviderClass() {
    return WarServletContextHandlerProvider.class;
  }


  protected ServletModule createServletModule() {
    return new ServletModule();
  }


}
