package com.fatheroctober.ppsim.common.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Profile("!endpoint")
public class LifecycleRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LifecycleRunner.class);

    private final Collection<Lifecycle> lifecycles;

    @Autowired
    public LifecycleRunner(Collection<Lifecycle> lifecycles) {
        this.lifecycles = lifecycles;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting lifecycles...");
        lifecycles.forEach(Lifecycle::start);
        logger.info("Lifecycles were started");
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Stopping lifecycles...");
        lifecycles.forEach(Lifecycle::stop);
        logger.info("Lifecycles were stopped");
    }
}
