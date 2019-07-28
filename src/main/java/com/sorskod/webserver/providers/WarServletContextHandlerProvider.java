package com.sorskod.webserver.providers;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sorskod.webserver.WarContextConfigurator;
import com.sorskod.webserver.annotations.ContextHandlerAttributes;
import com.sorskod.webserver.annotations.ContextHandlerInitParams;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;
import java.util.Set;
import java.util.logging.Filter;

/**
 * @author Aleksandar Babic
 */
public class WarServletContextHandlerProvider implements Provider<Handler> {

  private final ServletHolder servletHolder;
  private final WarContextConfigurator configurator;
  private Map<String, String> initContextParameters;
  private Map<String, String> initContextAttributes;
  @Inject
  private Provider<Injector> injector;

  @Inject
  public WarServletContextHandlerProvider(ServletHolder servletHolder,
                                          WarContextConfigurator configurator) {
    this.servletHolder = servletHolder;
    this.configurator = configurator;

  }

  @com.google.inject.Inject(optional = true)
  public void setInitContextParameters(@ContextHandlerInitParams Map<String, String> initContextParameters) {
    this.initContextParameters = initContextParameters;
  }

  @com.google.inject.Inject(optional = true)
  public void setInitContextAttributes(@Nullable @ContextHandlerAttributes Map<String, String> initContextAttributes) {
    this.initContextAttributes = initContextAttributes;
  }

   public Handler get() {
    WebAppContext handler = new WebAppContext();
    handler.setContextPath(configurator.getContextPath());
    handler.setWar(configurator.getWar());

    if (initContextParameters != null) {
      initContextParameters.forEach(handler::setInitParameter);
    }

    if (initContextAttributes != null) {
      initContextAttributes.forEach(handler::setAttribute);
    }

    handler.setTempDirectory(configurator.getTempDirectory());
    handler.setPersistTempDirectory(configurator.getPersistTempDirectory());
    handler.addEventListener(new GuiceServletContextListener() {
      @Override
      protected Injector getInjector() {
        return injector.get();
      }
    });

    handler.addServlet(servletHolder, configurator.getResourceBase() + "/*");

    return handler;
  }
}
