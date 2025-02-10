package com.objecteffects.redditgizmo.unaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.ShutdownEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SimpleEventListener {
    private static final Logger log
            = LoggerFactory.getLogger(SimpleEventListener.class);

    @Inject
    ApplicationContext applicationContext;

    @EventListener
    public void onStartupEvent(StartupEvent event) {
        log.info("onStartupEvent: {}",
                applicationContext.getEnvironment().getActiveNames());
    }

    @EventListener
    public void onShutdownEvent(ShutdownEvent event) {
        log.info("onShutdownEven {}",
                applicationContext.getEnvironment().getActiveNames());
    }
}