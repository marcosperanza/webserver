package com.anritsu.common.webserver;

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

    default boolean getParentLoaderPriority() {
        return false;
    }
}
