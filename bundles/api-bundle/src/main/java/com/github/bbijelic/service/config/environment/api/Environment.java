package com.github.bbijelic.service.config.environment.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Environment
 */
@Entity
@Table(name = "config_environment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}, name = "config_environment_name_unique")})
public class Environment {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_environment_sequence_generator")
    @SequenceGenerator(name = "config_environment_sequence_generator", sequenceName = "config_environment_seq")
    @Column(name = "id")
    @JsonIgnore
    private long id;

    /**
     * ID getter
     *
     * @return the id
     */
    public long getId(){
        return id;
    }

    /**
     * Id setter
     *
     * @param id the id
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Region name
     */
    @NotEmpty
    @JsonProperty("name")
    @Column(name = "name", insertable = true, nullable = false, updatable = true)
    private String name;

    /**
     * Name getter
     *
     * @return the region name
     */
    public String getName(){
        return name;
    }

    /**
     * Region setter
     *
     * @param name the region name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Description
     */
    @JsonProperty("description")
    @Column(name = "description", insertable = true, nullable = true, updatable = true)
    private String description;

    /**
     * Description getter
     *
     * @return the description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Description setter
     *
     * @param description the description
     */
    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id, name, description);
    }

    @Override
    public boolean equals(Object obj){
        return Objects.equal(obj, this);
    }

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .toString();
    }


}
