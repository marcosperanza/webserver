package com.anritsu.common.webserver.connectors.http;

import com.anritsu.common.webserver.connectors.AbstractConnectorModule;
import com.anritsu.common.webserver.connectors.ConnectorFactory;

/**
 * @author Aleksandar Babic
 */
public class HTTPConnectorModule extends AbstractConnectorModule {

  @Override
  protected Class<? extends ConnectorFactory> getConnectorFactoryClass() {
    return HTTPConnectorFactory.class;
  }
}
