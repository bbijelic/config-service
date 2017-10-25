package com.github.bbijelic.service.config;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bbijelic.service.config.application.repository.ApplicationRepository;
import com.github.bbijelic.service.config.application.resources.ApplicationResource;
import com.github.bbijelic.service.config.config.ServiceConfiguration;
import com.github.bbijelic.service.config.region.api.Region;
import com.github.bbijelic.service.config.region.repository.RegionRepository;
import com.github.bbijelic.service.config.region.resources.RegionResource;
import com.scottescue.dropwizard.entitymanager.EntityManagerBundle;

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
            new EntityManagerBundle<ServiceConfiguration>(
                Region.class, com.github.bbijelic.service.config.application.api.Application.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDatabaseConfiguration().getDataSourceFactory();
        }
    };
            
    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)));
                        
        // Add entity manager bundle
        bootstrap.addBundle(entityManagerBundle);
    }
    
    @Override
    public void run(ServiceConfiguration config, Environment env) throws Exception {
        LOGGER.info("Starting " + getName()); 
        
        // Get instance of shared entity manager
        final EntityManager sharedEntityManager = entityManagerBundle.getSharedEntityManager();
        
        // Region repository instance
        final RegionRepository regionRepository = new RegionRepository(Region.class, sharedEntityManager);
        // Application repository instance
        final ApplicationRepository applicationRepository = new ApplicationRepository(
            com.github.bbijelic.service.config.application.api.Application.class, sharedEntityManager);
        
        // Add region resource to the jersey environment
        env.jersey().register(new RegionResource(regionRepository));
        env.jersey().register(new ApplicationResource(applicationRepository, regionRepository));
    }
}
