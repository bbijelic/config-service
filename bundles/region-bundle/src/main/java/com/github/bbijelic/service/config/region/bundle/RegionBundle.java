package com.github.bbijelic.service.config.region.bundle;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bbijelic.service.config.region.api.Region;
import com.github.bbijelic.service.config.region.repository.RegionRepository;
import com.github.bbijelic.service.config.region.resources.RegionResource;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Region dropwizard bundle
 * 
 * @author Bojan Bijelic
 * @since 1.0.0
 */
public class RegionBundle implements Bundle {
    
    /**
     * Entity Manager
     */
    private EntityManager entityManager;
    
    /**
     * Constructor
     * 
     * @param entityManager the entity manager
     */
    public RegionBundle(final EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionBundle.class);
    
    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        LOGGER.info("RegionBundle initialized");
    }
    
    @Override
    public void run(Environment environment) {
        
        // Register region resource
        environment.jersey().register(new RegionResource(new RegionRepository(Region.class, entityManager)));        
        
        LOGGER.info("RegionBundle run");        
    }
}
