package com.fatheroctober.ppsim.consumer;

import com.fatheroctober.ppsim.common.engine.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ConsumerRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerRunner.class);

    private final Collection<Lifecycle> lifecycles;

    @Autowired
    public ConsumerRunner(Collection<Lifecycle> lifecycles) {
        this.lifecycles = lifecycles;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting consuming...");
        lifecycles.forEach(Lifecycle::start);
        logger.info("Consuming were started");
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Stopping consuming...");
        lifecycles.forEach(Lifecycle::stop);
        logger.info("Consuming were stopped");
    }
}
