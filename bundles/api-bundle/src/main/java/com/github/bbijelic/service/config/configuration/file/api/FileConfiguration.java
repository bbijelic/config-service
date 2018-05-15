package com.github.bbijelic.service.config.configuration.file.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
     * Timestamp
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", insertable = true, nullable = false, updatable = false)
    private Calendar timestamp;

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
    @NotEmpty
    @JsonProperty("name")
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

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("timestamp", timestamp.toInstant().toString())
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
