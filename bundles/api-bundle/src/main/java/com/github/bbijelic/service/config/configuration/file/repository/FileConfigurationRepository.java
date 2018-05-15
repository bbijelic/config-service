package com.github.bbijelic.service.config.configuration.file.repository;

import com.github.bbijelic.service.config.configuration.file.api.FileConfiguration;
import com.github.bbijelic.service.config.core.repository.JpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

/**
 * File configuration repository
 *
 * @author Bojan Bijelić
 * @since 1.0.0
 */
public class FileConfigurationRepository extends JpaRepository<FileConfiguration> {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileConfigurationRepository.class);

    /**
     * Constructor
     *
     * @param type
     * @param entityManager
     */
    public FileConfigurationRepository(Class<FileConfiguration> type, EntityManager entityManager){
        super(type, entityManager);
    }
}
