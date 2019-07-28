package com.sorskod.webserver.connectors.https;

import com.sorskod.webserver.connectors.AbstractConnectorModule;
import com.sorskod.webserver.connectors.ConnectorFactory;

/**
 * @author Aleksandar Babic
 */
public class HTTPSConnectorModule extends AbstractConnectorModule {

  public enum Type {
    SIMPLE,
    KEYSTORE
  }

  private final Type type;

  public HTTPSConnectorModule() {
    this(Type.SIMPLE);
  }

  public HTTPSConnectorModule(Type type) {
    this.type = type;
  }

  @Override
  protected Class<? extends ConnectorFactory> getConnectorFactoryClass() {
    if (type == Type.KEYSTORE) {
      return HTTPSKeyStoreConnectorFactory.class;
    }
    return HTTPSConnectorFactory.class;
  }
}
