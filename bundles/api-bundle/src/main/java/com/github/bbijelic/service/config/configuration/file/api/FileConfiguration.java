package com.github.bbijelic.service.config.configuration.file.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.bbijelic.service.config.application.api.Application;
import com.github.bbijelic.service.config.environment.api.Environment;
import com.github.bbijelic.service.config.region.api.Region;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;

/**
 * File configuration
 */
@Entity
@Table(name = "config_file_configuration")
public class FileConfiguration {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_file_config_sequence_generator")
    @SequenceGenerator(name = "config_file_config_sequence_generator", sequenceName = "config_file_config_seq")
    @Column(name = "id")
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
     * Timestamp
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", insertable = true, nullable = false, updatable = false)
    private Calendar timestamp = Calendar.getInstance();

    /**
     * Timestamp getter
     *
     * @return the timestamp
     */
    public Calendar getTimestamp(){
        return timestamp;
    }

    /**
     * Name
     */
    @Column(name = "name", insertable = true, nullable = false, updatable = true)
    private String name;

    /**
     * Name getter
     *
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Region setter
     *
     * @param name the name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Content
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", insertable = true, nullable = false, updatable = false)
    private byte[] content;

    /**
     * Content getter
     *
     * @return the content bytes
     */
    public byte[] getContent(){
        return content;
    }

    /**
     * Content setter
     *
     * @param content the content
     */
    public void setContent(byte[] content){
        this.content = content;
    }

    /**
     * Environment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "config_file_configuration__environment_fk"))
    private Environment environment;

    /**
     * Environment getter
     *
     * @return the environment
     */
    public Environment getEnvironment(){
        return environment;
    }

    /**
     * Environment setter
     *
     * @param environment the environment
     */
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    /**
     * Region
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "config_file_configuration__region_fk"))
    private Region region;

    /**
     * Region getter
     *
     * @return the getter
     */
    public Region getRegion(){
        return region;
    }

    /**
     * Region setter
     *
     * @param region the region
     */
    public void setRegion(Region region){
        this.region = region;
    }

    /**
     * Application
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    /**
     * Application getter
     *
     * @return the application
     */
    public Application getApplication(){
        return application;
    }

    /**
     * Application setter
     *
     * @param application the application
     */
    public void setApplication(Application application){
        this.application = application;
    }

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("timestamp", timestamp.toInstant().toString())
                .add("name", name)
                .toString();
    }
}
