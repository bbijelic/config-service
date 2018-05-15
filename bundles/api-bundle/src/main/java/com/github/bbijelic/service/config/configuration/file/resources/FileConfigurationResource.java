package com.github.bbijelic.service.config.configuration.file.resources;

import com.github.bbijelic.service.config.configuration.file.api.FileConfiguration;
import com.github.bbijelic.service.config.configuration.file.repository.FileConfigurationRepository;
import com.github.bbijelic.service.config.core.repository.RepositoryException;
import com.scottescue.dropwizard.entitymanager.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * File configuration resource
 *
 * @author Bojan Bijelić
 * @since 1.0.0
 */
@Path("/file-configuration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FileConfigurationResource {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileConfigurationResource.class);

    /**
     * File configuration repository
     */
    private final FileConfigurationRepository fileConfigurationRepository;

    /**
     * Constructor
     *
     * @param fileConfigurationRepository the file configuration repository
     */
    public FileConfigurationResource(
            final FileConfigurationRepository fileConfigurationRepository){

        this.fileConfigurationRepository = fileConfigurationRepository;
    }

    /**
     * Adds new file configuration
     *
     * @param fileConfiguration the file configuration entity
     */
    @POST
    @UnitOfWork
    public Response addFileConfiguration(
            @Valid FileConfiguration fileConfiguration){

        // Prepare response
        Response response = Response.status(Response.Status.CREATED).build();

        try {

            // Persist
            fileConfigurationRepository.persist(fileConfiguration);

        } catch (RepositoryException re) {
            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

}
