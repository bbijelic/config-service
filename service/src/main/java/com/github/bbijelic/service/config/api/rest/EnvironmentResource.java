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

import com.github.bbijelic.service.config.entity.Environment;

/**
 * Environment resource
 * 
 * @author Bojan Bijelic
 */
@Path("/environment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnvironmentResource {

    /**
     * Adds new Environment
     */
    @POST
    public Response addEnvironment(
        @NotNull @QueryParam("region") String region, 
        @NotNull @QueryParam("application") String application, 
        @Valid Environment environment){
            
        return Response.ok().build();
    }
    
    /**
     * Gets one or list of Environment
     */
    @GET
    public Response getEnvironment(
        @NotNull @QueryParam("region") String region,
        @NotNull @QueryParam("application") String application, 
        @QueryParam("name") Optional<String> name){
            
        return Response.ok().build();        
    }
    
    /**
     * Updates existing Environment
     */
    @PUT
    public Response updateEnvironment(
        @NotNull @QueryParam("region") String region, 
        @NotNull @QueryParam("application") String application, 
        @NotNull @QueryParam("name") String name, 
        Environment environment){
            
        return Response.ok().build();
    }

    /**
     * Deletes existing Environment
     */
    @DELETE
    public Response deleteEnvironment(
        @NotNull @QueryParam("region") String region, 
        @NotNull @QueryParam("application") String application, 
        @NotNull @QueryParam("name") String name){
            
        return Response.ok().build();
    }
}
