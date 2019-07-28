package com.sorskod.webserver.connectors.https;

import com.sorskod.webserver.SslConfigurator;
import com.sorskod.webserver.annotations.BaseConfiguration;
import com.sorskod.webserver.annotations.SecureConnector;
import com.sorskod.webserver.connectors.ConnectorFactory;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.inject.Inject;


/**
 * Connector Factory for configure a SSL connector usin a key store.
 */
public class HTTPSKeyStoreConnectorFactory implements ConnectorFactory {

  private final SslConfigurator configurator;
  private final HttpConfiguration defaultConfig;

  @Inject
  public HTTPSKeyStoreConnectorFactory(@SecureConnector SslConfigurator configurator,
                                       @BaseConfiguration HttpConfiguration defaultConfig) {
    this.configurator = configurator;
    this.defaultConfig = defaultConfig;
  }


  @Override
  public ServerConnector get(Server server) {
    HttpConfiguration http_config = new HttpConfiguration();
    http_config.setSecureScheme(HttpScheme.HTTPS.asString());
    http_config.setSecurePort(configurator.getPort());
    http_config.setOutputBufferSize(32768);
    http_config.setRequestHeaderSize(8192);
    http_config.setResponseHeaderSize(8192);
    http_config.setSendServerVersion(true);
    http_config.setSendDateHeader(false);

    HttpConfiguration https_config = new HttpConfiguration(http_config);
    https_config.addCustomizer(new SecureRequestCustomizer());

    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStorePath(configurator.getKeystoreFile());
    sslContextFactory.setKeyStorePassword(configurator.getKeyStorePassword());
    sslContextFactory.setKeyManagerPassword(configurator.getKeyManagerPassword());
    sslContextFactory.setTrustStorePath(configurator.getTrustStorePath());
    sslContextFactory.setTrustStorePassword(configurator.getTrustStorePassword());

    ServerConnector sslConnector = new ServerConnector(server,
            new SslConnectionFactory(sslContextFactory, "http/1.1"),
            new HttpConnectionFactory(https_config));
    sslConnector.setPort(configurator.getPort());
    server.addConnector(sslConnector);


    HttpConfiguration configuration = new HttpConfiguration(defaultConfig);
    configuration.setSecurePort(configurator.getPort());
    configuration.setSecureScheme(HttpScheme.HTTPS.asString());


    return sslConnector;
  }
}