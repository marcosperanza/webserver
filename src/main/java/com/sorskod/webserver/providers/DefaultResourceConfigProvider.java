package com.sorskod.webserver.providers;

import com.google.inject.Injector;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.Feature;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Default RC provider is about to instantiate {@link ResourceConfig}
 * with all Guice bound JAX-RS {@link Feature} instances
 *
 * @author Aleksandar Babic
 */
public class DefaultResourceConfigProvider implements Provider<ResourceConfig> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResourceConfigProvider.class);

  private Set<Feature> features = new HashSet<>();
  private Set<Class<? extends Feature>> featureClasses = new HashSet<>();
  private final Injector injector;

  @Inject
  public DefaultResourceConfigProvider(Injector injector) {
    this.injector = injector;
  }

  @com.google.inject.Inject(optional = true)
  public void setFeatures(Set<Feature> features) {
    this.features = features;
  }

  @com.google.inject.Inject(optional = true)
  public void setFeatureClasses(Set<Class<? extends Feature>> featureClasses) {
    this.featureClasses = featureClasses;
  }

  @Override
  public ResourceConfig get() {
    ResourceConfig config = new ResourceConfig();

    // Still considering to disable auto discovery...
    //config.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);

    Stream.concat(features.stream(), featureClasses.stream().map(injector::getInstance))
        .peek(f -> LOGGER.info("Register feature: {}", f))
        .forEach(config::register);

    return config;
  }
}
