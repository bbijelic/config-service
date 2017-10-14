package com.github.bbijelic.service.config.entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Application
 * 
 * @author Bojan Bijelic
 */
public class Application {
    
    /**
     * Application name
     */
    @NotEmpty
    @JsonProperty("name")
    private String name;
    
    /**
     * Name getter
     * @return the application name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Name setter
     * @param name the application name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Description
     */
    @NotEmpty
    @JsonProperty("description")
    private String description;
    
    /**
     * Description getter
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Description setter
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("description", description)
            .toString();
    }
}
