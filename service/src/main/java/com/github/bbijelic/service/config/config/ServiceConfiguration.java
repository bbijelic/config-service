package com.github.bbijelic.service.config.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.bbijelic.service.config.core.db.DatabaseConfiguration;

import io.dropwizard.Configuration;

/**
 * Service configuration class
 * 
 * @author Bojan Bijelic
 */
public class ServiceConfiguration extends Configuration {
    
    /**
     * Database configuration
     */
    @Valid
    @NotNull
    @JsonProperty("database")
    private DatabaseConfiguration databaseConfiguration;
    
    /**
     * Database configuration getter
     * @return the database configuration
     */
    public DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }
    
    /**
     * Database configuration setter
     * @param databaseConfiguration the database configuration
     */
    public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }
    
}
