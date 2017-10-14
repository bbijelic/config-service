package com.github.bbijelic.service.config.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Service configuration class
 * 
 * @author Bojan Bijelic
 */
public class ServiceConfiguration extends Configuration {
    
    /**
     * Data source factory
     */
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database;

    /**
     * Returns data source factory
     * @return the data source factory
     */
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
}
