package com.github.bbijelic.service.config.application.resources;

import com.github.bbijelic.service.config.application.api.Application;
import com.github.bbijelic.service.config.application.repository.ApplicationRepository;
import com.github.bbijelic.service.config.core.repository.RepositoryException;
import com.scottescue.dropwizard.entitymanager.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;

/**
 * Application resource
 *
 * @author Bojan Bijelic
 */
@Path("/application")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationResource.class);

    /**
     * Applications repository
     */
    private ApplicationRepository applicationRepository;

    /**
     * Constructor
     *
     * @param applicationRepository the application repository
     */
    public ApplicationResource(
            final ApplicationRepository applicationRepository){

        this.applicationRepository = applicationRepository;
    }

    /**
     * Adds new application
     *
     * @param application the application entity
     */
    @POST
    @UnitOfWork
    public Response addApplication(
            @Valid Application application){

        // Prepare response
        Response response = Response.status(Status.CREATED).build();

        try {

            // Persist
            applicationRepository.persist(application);

        } catch (RepositoryException re) {
            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

    /**
     * Gets one or list of applications
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
    public Response getApplication(
            @QueryParam("name") Optional<String> nameOptional,
            @QueryParam("offset") Optional<Integer> offsetOptional,
            @QueryParam("limit") Optional<Integer> limitOptional,
            @QueryParam("sort_by") Optional<String> sortByOptional,
            @QueryParam("sort_order") Optional<String> sortOrderOptional){

        // Prepare response
        Response response = Response.ok().build();

        try {

            if (nameOptional.isPresent()) {
                // Get single application identified by the name
                Optional<Application> result = applicationRepository.getByName(nameOptional.get());

                if (result.isPresent()) {
                    // Application found, return it as a entity body
                    response = Response.ok(result.get()).build();

                } else {
                    // Application not found, return 404
                    response = Response.status(Status.NOT_FOUND).build();
                }

            } else {

                // Get all the applications                
                List<Application> resultList = applicationRepository.getAll(
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
     * Updates existing application
     *
     * @param name        the application name
     * @param application the application entity
     */
    @PUT
    @UnitOfWork
    public Response updateApplication(
            @NotNull @QueryParam("name") String name,
            @Valid Application application){

        // Prepare response
        Response response = Response.ok().build();

        try {

            // Find application first
            Optional<Application> applicationOptional = applicationRepository.getByName(name);

            if (applicationOptional.isPresent()) {

                // Update found application with properties from the request
                Application applicationEntity = applicationOptional.get();
                applicationEntity.setName(application.getName());
                applicationEntity.setDescription(application.getDescription());

            } else {

                // Application not found
                response = Response.status(Status.NOT_FOUND).build();
            }

        } catch (RepositoryException re) {

            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }

    /**
     * Deletes existing application
     *
     * @param name the application name
     * @return the response
     */
    @DELETE
    @UnitOfWork
    public Response deleteApplication(
            @NotNull @QueryParam("name") String name){

        // Prepare response
        Response response = Response.noContent().build();

        try {

            // Find application first
            Optional<Application> applicationOptional = applicationRepository.getByName(name);

            if (applicationOptional.isPresent()) {
                // Delete the application
                applicationRepository.remove(applicationOptional.get());
                // Application successfully deleted
                response = Response.noContent().build();

            } else {
                // Application not found
                response = Response.status(Status.NOT_FOUND).build();
            }

        } catch (RepositoryException re) {

            LOGGER.error(re.getMessage(), re.toString());
            // Server error response
            response = Response.serverError().build();
        }

        return response;
    }
}
