package com.anritsu.common.webserver.providers;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JettyServletContextListener extends GuiceResteasyBootstrapServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent event) {
        // We access the injector that was attached to the context by GuiceServletContextListener
        Injector parentInjector = (Injector) event.getServletContext().getAttribute(Injector.class.getName());
        parentInjector.injectMembers(this);
        super.contextInitialized(event);
    }

    @Override
    protected List<? extends Module> getModules(ServletContext context) {
        List<? extends Module> modules = super.getModules(context);
        Injector parentInjector = (Injector) context.getAttribute(Injector.class.getName());

        List<? extends Module> applicationModules = getApplicationModules(parentInjector);

        List<Module> res = new ArrayList<>();
        res.addAll(modules);
        res.addAll(applicationModules);
        return res;
    }

    protected List<? extends Module> getApplicationModules(Injector jettyInjector) {
        return new ArrayList<>(1);
    }


}