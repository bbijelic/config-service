package com.github.bbijelic.service.config.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Environment
 * 
 * @author Bojan Bijelic
 */
public class Environment {
    
    /**
     * Environment name
     */
    @NotEmpty
    @JsonProperty("name")
    private String name;
    
    /**
     * Name getter
     * @return the Environment name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Name setter
     * @param name the Environment name
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
