package com.sorskod.webserver;

import java.io.File;

/**
 * Configurator interface for deploy a war into the jetty instance!
 *
 * @author Marco Speranza
 */
public interface WarContextConfigurator extends ContextConfigurator {

    String getWar();

    File getTempDirectory();

    boolean getPersistTempDirectory();
}
