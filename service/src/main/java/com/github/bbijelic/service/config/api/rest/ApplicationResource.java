package com.github.bbijelic.service.config.api.rest;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.github.bbijelic.service.config.entity.Application;

/**
 * Application resource
 * 
 * @author Bojan Bijelic
 */
@Path("/application")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    /**
     * Adds new application
     */
    @POST
    public Response addApplication(
        @NotNull @QueryParam("region") String region, 
        @Valid Application application){
            
        return Response.ok().build();
    }
    
    /**
     * Gets one or list of application
     */
    @GET
    public Response getApplication(
        @NotNull @QueryParam("region") String region, 
        @QueryParam("name") Optional<String> name){
            
        return Response.ok().build();        
    }
    
    /**
     * Updates existing application
     */
    @PUT
    public Response updateApplication(
        @NotNull @QueryParam("region") String region, 
        @NotNull @QueryParam("name") String name, 
        Application application){
            
        return Response.ok().build();
    }

    /**
     * Deletes existing application
     */
    @DELETE
    public Response deleteApplication(
        @NotNull @QueryParam("region") String region, 
        @NotNull @QueryParam("name") String name){
            
        return Response.ok().build();
    }
}
