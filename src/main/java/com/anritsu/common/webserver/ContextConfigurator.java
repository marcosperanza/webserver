package com.anritsu.common.webserver;

import java.io.File;

/**
 * Configurator interface for defining
 * - the resources base path
 * - context root path
 * - the war location
 * - the temporary folder where the war will be expoded
 *
 * @author Marco Speranza
 * @version : $
 */
public interface ContextConfigurator {

    /**
     * Defines the root web app context path
     * @return the root web app context path
     */
    default String getContextPath() {
        return "/";
    }

    /**
     * Defines the root resources
     * @return
     */
    default String getResourceBase() {return "/api";}


    /**
     * Defines the path where the war file is located
     * @return the path where the war file is located
     */
    String getWar();

    /**
     * Defines the temporary path
     * @return the temporary path
     */
    File getTempDirectory();
}
