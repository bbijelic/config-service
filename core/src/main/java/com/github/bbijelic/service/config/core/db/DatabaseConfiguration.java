package com.github.bbijelic.service.config.core.db;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Database configuration.
 * 
 * @author Bojan Bijelic
 * @since 1.0.0
 */
public class DatabaseConfiguration extends Configuration {
    
    /**
     * Data source factory
     */
    @Valid
    @NotNull
    @JsonProperty("datasource")
    private DataSourceFactory database;

    /**
     * Returns data source factory
     * @return the data source factory
     */
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
}
