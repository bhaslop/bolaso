package com.bolaso.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

public class HerokuDatabaseConfiguration implements EnvironmentAware {

    private final Logger log = LoggerFactory
        .getLogger(HerokuDatabaseConfiguration.class);

    private PropertyResolver propertyResolver;


    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new Proper
    }
}
