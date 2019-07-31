/*
 * : MyJettyServletContextListener.java 29021 2013-04-10 15:47:16Z masp $
 *
 * Copyright 1998-2006 by Anritsu A/S,
 * Kirkebjerg Alle 86, DK-2605 Broendby, Denmark.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Anritsu A/S.
 */
package com.anritsu.common.webserver;

import com.anritsu.common.webserver.providers.JettyServletContextListener;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.List;

/**
 * TODO FILL ME!
 *
 * @author Marco Speranza
 * @version : $
 */
public class MyJettyServletContextListener extends JettyServletContextListener {
    @Override
    protected List<? extends Module> getApplicationModules(Injector jettyInjector) {
        return super.getApplicationModules(jettyInjector);
    }
}
