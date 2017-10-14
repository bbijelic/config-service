package com.github.bbijelic.service.config;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bbijelic.service.config.api.rest.RegionResource;
import com.github.bbijelic.service.config.config.ServiceConfiguration;
import com.github.bbijelic.service.config.model.Region;
import com.github.bbijelic.service.config.repository.RegionRepository;
import com.scottescue.dropwizard.entitymanager.EntityManagerBundle;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
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
    
    /**
     * Entity Manager Bundle
     */
    private final EntityManagerBundle<ServiceConfiguration> entityManagerBundle = 
            new EntityManagerBundle<ServiceConfiguration>(Region.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
        
    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)));
                
        // Initialize entity manager bundle
        bootstrap.addBundle(entityManagerBundle);
    }
    
    @Override
    public void run(ServiceConfiguration config, Environment env) throws Exception {
        LOGGER.info("Starting " + getName());
        
        // Entity manager
        final EntityManager entityManager = entityManagerBundle.getSharedEntityManager();
        
        // Register region resource
        env.jersey().register(new RegionResource(new RegionRepository(Region.class, entityManager)));        
    }
}
