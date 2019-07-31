package com.anritsu.common.webserver;

/**
 * Extends the standard {@link Configurator} interface to handle the configuration for keystore.
 *
 * @author Marco Speranza
 */
public interface SslConfigurator extends Configurator {

    /**
     * Returns the keystore path
     * @return the keystore path
     */
    String getKeystoreFile();

    /**
     * Returns the key store password
     * @return the key store password
     */
    String getKeyStorePassword();

    /**
     * Returns the key store manager password
     * @return the key store manager password
     */
    String getKeyManagerPassword();


    /**
     * Returns the key store trust path
     * @return the key store trust path
     */
    String getTrustStorePath();

    /**
     * Returns the key store trust password
     * @return the key store trust password
     */
    String getTrustStorePassword();
}
