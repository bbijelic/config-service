package com.github.bbijelic.service.config.environment.resources;

import com.github.bbijelic.service.config.core.repository.RepositoryException;
import com.github.bbijelic.service.config.environment.api.Environment;
import com.github.bbijelic.service.config.environment.repository.EnvironmentRepository;
import com.scottescue.dropwizard.entitymanager.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Environment resource
 *
 * @author Bojan Bijelic
 * @since 1.0.0
 */
@Path("/environment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnvironmentResource {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentResource.class);

    /**
     * Environment repository
     */
    private final EnvironmentRepository environmentRepository;


    /**
     * Constructor
     *
     * @param environmentRepository the environment repository
     */
    public EnvironmentResource(
            final EnvironmentRepository environmentRepository){

        this.environmentRepository = environmentRepository;
    }

    /**
     * Adds new environment
     *
     * @param environment the environment entity
     */
    @POST
    @UnitOfWork
    public Response addEnvironment(
            @Valid Environment environment){

        // Prepare response
        Response response = Response.status(Response.Status.CREATED).build();

        try {

            // Persist
            environmentRepository.persist(environment);

        } catch (RepositoryException re) {
            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

    /**
     * Gets one or list of environments
     *
     * @param nameOptional      the optional name
     * @param offsetOptional    the optional offset for the pagination
     * @param limitOptional     the optional limit for the pagination
     * @param sortByOptional    the optional sort by field
     * @param sortOrderOptional the optional sort order
     * @return the response
     */
    @GET
    @UnitOfWork(transactional = false)
    public Response getEnvironment(
            @QueryParam("name") Optional<String> nameOptional,
            @QueryParam("offset") Optional<Integer> offsetOptional,
            @QueryParam("limit") Optional<Integer> limitOptional,
            @QueryParam("sort_by") Optional<String> sortByOptional,
            @QueryParam("sort_order") Optional<String> sortOrderOptional){

        // Prepare response
        Response response = Response.ok().build();

        try {

            if (nameOptional.isPresent()) {
                // Get single environment identified by the name
                Optional<Environment> result = environmentRepository.getByName(nameOptional.get());

                if (result.isPresent()) {
                    // Environment found, return it as a entity body
                    response = Response.ok(result.get()).build();

                } else {
                    // Environment not found, return 404
                    response = Response.status(Response.Status.NOT_FOUND).build();
                }

            } else {

                // Get all the environments
                List<Environment> resultList = environmentRepository.getAll(
                        offsetOptional,
                        limitOptional,
                        sortByOptional,
                        sortOrderOptional
                );

                // Return it as a entity body
                response = Response.ok(resultList).build();
            }

        } catch (RepositoryException re) {

            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

    /**
     * Updates existing environment
     *
     * @param name        the environment name
     * @param environment the environment entity
     */
    @PUT
    @UnitOfWork
    public Response updateEnvironment(
            @NotNull @QueryParam("name") String name,
            @Valid Environment environment){

        // Prepare response
        Response response = Response.ok().build();

        try {

            // Find environment first
            Optional<Environment> environmentOptional = environmentRepository.getByName(name);

            if (environmentOptional.isPresent()) {

                // Update found environment with properties from the request
                Environment environmentEntity = environmentOptional.get();
                environmentEntity.setName(environment.getName());
                environmentEntity.setDescription(environment.getDescription());

            } else {

                // Environment not found
                response = Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (RepositoryException re) {

            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

    /**
     * Deletes existing environment
     *
     * @param name the environment name
     * @return the response
     */
    @DELETE
    @UnitOfWork
    public Response deleteEnvironment(
            @NotNull @QueryParam("name") String name){

        // Prepare response
        Response response = Response.noContent().build();

        try {

            // Find environment first
            Optional<Environment> environmentOptional = environmentRepository.getByName(name);

            if (environmentOptional.isPresent()) {
                // Delete the application
                environmentRepository.remove(environmentOptional.get());
                // Application successfully deleted
                response = Response.noContent().build();

            } else {
                // Environment not found
                response = Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (RepositoryException re) {

            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

}
