/*
 * : DefaultFeatureModule.java 29021 2013-04-10 15:47:16Z masp $
 *
 * Copyright 1998-2006 by Anritsu A/S,
 * Kirkebjerg Alle 86, DK-2605 Broendby, Denmark.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Anritsu A/S.
 */
package com.sorskod.webserver.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.ProvidesIntoSet;
import com.sorskod.webserver.features.BoundWebResourcesFeature;
import com.sorskod.webserver.features.JsonJacksonFeature;
import com.sorskod.webserver.mappers.RuntimeExceptionMapper;
import com.sorskod.webserver.mappers.WebApplicationExceptionMapper;

import javax.ws.rs.core.Feature;

/**
 * Install the default provided {@link Feature}
 *
 * @author Marco Speranza
 */
public class DefaultFeatureModule extends AbstractModule {

    @ProvidesIntoSet
    Class<? extends Feature> jacksonFeature() {
        return JsonJacksonFeature.class;
    }

    @ProvidesIntoSet
    Class<? extends Feature> boundWebResourcesFeature() {
        return BoundWebResourcesFeature.class;
    }

    @ProvidesIntoSet
    Class<? extends Feature> runTimeExceptionMapperFeature() {
        return RuntimeExceptionMapper.class;
    }

    @ProvidesIntoSet
    Class<? extends Feature> webAppExceptionFeature() {
        return WebApplicationExceptionMapper.class;
    }
}
