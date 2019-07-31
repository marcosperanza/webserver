package com.anritsu.common.webserver.connectors.https;

import com.anritsu.common.webserver.annotations.BaseConfiguration;
import com.anritsu.common.webserver.annotations.SecureConnector;
import com.anritsu.common.webserver.connectors.ConnectorFactory;
import com.anritsu.common.webserver.Configurator;

import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import javax.inject.Inject;

/**
 * @author Aleksandar Babic
 */
public class HTTPSConnectorFactory implements ConnectorFactory {

  private final Configurator configurator;
  private final HttpConfiguration defaultConfig;

  @Inject
  public HTTPSConnectorFactory(@SecureConnector Configurator configurator,
                               @BaseConfiguration HttpConfiguration defaultConfig) {
    this.configurator = configurator;
    this.defaultConfig = defaultConfig;
  }


  @Override
  public ServerConnector get(Server server) {
    HttpConfiguration configuration = new HttpConfiguration(defaultConfig);
    configuration.setSecurePort(configurator.getPort());
    configuration.setSecureScheme(HttpScheme.HTTPS.asString());

    final ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(configuration));
    connector.setPort(configurator.getPort());
    connector.setHost(configurator.getHost());

    return connector;
  }
}
