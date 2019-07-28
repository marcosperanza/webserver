package com.sorskod.webserver.providers;

import com.google.inject.Provider;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author Aleksandar Babic
 */
public class ServletHolderProvider implements Provider<ServletHolder> {

  private final ResourceConfig resourceConfig;

  @Inject
  public ServletHolderProvider(ResourceConfig resourceConfig) {
    this.resourceConfig = resourceConfig;
  }


  public ServletHolder get() {
    ServletContainer servlet = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servlet);
    servletHolder.setInitOrder(0);

    return servletHolder;
  }
}
