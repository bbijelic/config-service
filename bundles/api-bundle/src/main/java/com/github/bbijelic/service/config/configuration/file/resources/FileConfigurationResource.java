package com.github.bbijelic.service.config.configuration.file.resources;

import com.github.bbijelic.service.config.application.api.Application;
import com.github.bbijelic.service.config.application.repository.ApplicationRepository;
import com.github.bbijelic.service.config.configuration.file.api.FileConfiguration;
import com.github.bbijelic.service.config.configuration.file.repository.FileConfigurationRepository;
import com.github.bbijelic.service.config.core.repository.RepositoryException;
import com.github.bbijelic.service.config.environment.api.Environment;
import com.github.bbijelic.service.config.environment.repository.EnvironmentRepository;
import com.github.bbijelic.service.config.region.api.Region;
import com.github.bbijelic.service.config.region.repository.RegionRepository;
import com.scottescue.dropwizard.entitymanager.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
     * Application repository
     */
    private final ApplicationRepository applicationRepository;

    /**
     * Region repository
     */
    private final RegionRepository regionRepository;

    /**
     * Environment repository
     */
    private final EnvironmentRepository environmentRepository;

    /**
     * Constructor
     *
     * @param fileConfigurationRepository the file configuration repository
     * @param applicationRepository       the application repository
     * @param regionRepository            the region repository
     * @param environmentRepository       the environment repository
     */
    public FileConfigurationResource(
            final FileConfigurationRepository fileConfigurationRepository,
            final ApplicationRepository applicationRepository,
            final RegionRepository regionRepository,
            final EnvironmentRepository environmentRepository){

        this.fileConfigurationRepository = fileConfigurationRepository;
        this.applicationRepository = applicationRepository;
        this.regionRepository = regionRepository;
        this.environmentRepository = environmentRepository;
    }

    /**
     * Adds new file configuration
     *
     * @param application the application
     * @param environment the environment
     * @param region      the region
     * @param name        the configuration file name
     * @param fileContent the file configuration content
     */
    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response addFileConfiguration(
            final @NotNull @QueryParam("application") String application,
            final @NotNull @QueryParam("environment") String environment,
            final @NotNull @QueryParam("region") String region,
            final @NotNull @QueryParam("name") String name,
            final @NotNull byte[] fileContent){

        // Prepare response
        Response response = Response.status(Response.Status.CREATED).build();

        try {

            // Get application by name
            Optional<Application> applicationOptional = applicationRepository.getByName(application);

            // Get region by name
            Optional<Region> regionOptional = regionRepository.getByName(region);

            // Get environment by name
            Optional<Environment> environmentOptional = environmentRepository.getByName(environment);

            if (applicationOptional.isPresent()
                    && regionOptional.isPresent()
                    && environmentOptional.isPresent()) {

                // File configuration
                final FileConfiguration fileConfiguration = new FileConfiguration();
                fileConfiguration.setName(name);
                fileConfiguration.setApplication(applicationOptional.get());
                fileConfiguration.setEnvironment(environmentOptional.get());
                fileConfiguration.setRegion(regionOptional.get());
                fileConfiguration.setContent(fileContent);

                // Persist file configuration
                fileConfigurationRepository.persist(fileConfiguration);
            }


        } catch (RepositoryException re) {
            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

}
