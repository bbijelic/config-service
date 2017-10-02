package com.github.bbijelic.service.config;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bbijelic.service.config.config.ServiceConfiguration;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Configuration service main class
 * 
 * @author Bojan Bijelic
 */
public class ConfigService extends Application<ServiceConfiguration> {
    
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);
    
    /**
     * Main
     * 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new ConfigService().run(args);
    }
    
    /**
     * Service instance uuid
     */
    private UUID serviceInstanceUUID;
    
    /**
     * Constructor
     */
    public ConfigService() {
        serviceInstanceUUID = UUID.randomUUID();
    }
    
    @Override
    public String getName() {
        return "config-service-" + serviceInstanceUUID.toString();
    }
        
    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)));
    }
    
    @Override
    public void run(ServiceConfiguration arg0, Environment arg1) throws Exception {
        LOGGER.info("Starting " + getName());
        
    }
}
