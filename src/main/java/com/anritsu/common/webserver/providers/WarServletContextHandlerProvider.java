package com.anritsu.common.webserver.providers;

import com.anritsu.common.webserver.annotations.ContextHandlerAttributes;
import com.anritsu.common.webserver.annotations.ContextHandlerInitParams;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.anritsu.common.webserver.WarContextConfigurator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

/**
 * @author Aleksandar Babic
 */
public class WarServletContextHandlerProvider implements Provider<Handler> {

  private final WarContextConfigurator configurator;
  private Map<String, String> initContextParameters;
  private Map<String, String> initContextAttributes;
  @Inject
  private Provider<Injector> injector;

  @Inject
  public WarServletContextHandlerProvider(WarContextConfigurator configurator) {
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
    handler.setParentLoaderPriority(configurator.getParentLoaderPriority());
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

    return handler;
  }
}
