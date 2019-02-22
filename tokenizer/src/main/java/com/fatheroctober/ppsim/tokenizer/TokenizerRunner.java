package com.fatheroctober.ppsim.tokenizer;

import com.fatheroctober.ppsim.tokenizer.stream.Lifecycle;
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
public class TokenizerRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TokenizerRunner.class);

    private final Collection<Lifecycle> lifecycles;

    @Autowired
    public TokenizerRunner(Collection<Lifecycle> lifecycles) {
        this.lifecycles = lifecycles;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting tokenizer...");
        lifecycles.forEach(Lifecycle::start);
        logger.info("Tokenizer were started");
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Stopping tokenizer...");
        lifecycles.forEach(Lifecycle::stop);
        logger.info("Tokenizer were stopped");
    }

}
