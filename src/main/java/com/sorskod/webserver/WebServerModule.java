package com.sorskod.webserver;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import com.sorskod.webserver.annotations.BaseConfiguration;
import com.sorskod.webserver.connectors.ConnectorFactory;
import com.sorskod.webserver.entities.Error;
import com.sorskod.webserver.mappers.JsonMappingExceptionMapper;
import com.sorskod.webserver.mappers.JsonParseExceptionMapper;
import com.sorskod.webserver.providers.DefaultResourceConfigProvider;
import com.sorskod.webserver.providers.HttpConfigurationProvider;
import com.sorskod.webserver.providers.JettyServerProvider;
import com.sorskod.webserver.providers.ServletContextHandlerProvider;
import com.sorskod.webserver.providers.ServletHolderProvider;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Provider;
import java.util.Set;

/**
 * @author Aleksandar Babic
 */
public class WebServerModule extends AbstractModule  {

  // Workaround for avoid NoClassDefFound when using com.sorskod.webserver.providers.WarServletContextHandlerProvider
  static {
    Class<JsonMappingExceptionMapper> c = JsonMappingExceptionMapper.class;
    Class<JsonParseExceptionMapper> c2 = JsonParseExceptionMapper.class;
    Class<Error> c3 = Error.class;
  }

  protected void configure() {
    install(createServletModule());

    requireBinding(Key.get(new TypeLiteral<Set<ConnectorFactory>>() {}));

    bind(ServletHolder.class)
        .toProvider(getServletHolderProviderClass())
        .asEagerSingleton();

    bind(Handler.class)
        .toProvider(getHandlerProviderClass())
        .asEagerSingleton();

    bind(Server.class)
        .toProvider(getJettyServerProviderClass())
        .asEagerSingleton();

    bind(ResourceConfig.class)
        .toProvider(resourceConfigProvider())
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
    return ServletContextHandlerProvider.class;
  }

  protected Class<ServletHolderProvider> getServletHolderProviderClass() {
    return ServletHolderProvider.class;
  }

  protected ServletModule createServletModule() {
    return new ServletModule();
  }

  /**
   * Defined class that is used as a provider for {@link ResourceConfig}
   *
   * @return {@link ResourceConfig} provider
   */
  protected Class<? extends Provider<ResourceConfig>> resourceConfigProvider() {
    return DefaultResourceConfigProvider.class;
  }
}
