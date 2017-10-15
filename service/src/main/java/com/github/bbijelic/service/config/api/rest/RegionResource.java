package com.github.bbijelic.service.config.api.rest;

import java.util.List;
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
import javax.ws.rs.core.Response.Status;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.bbijelic.service.config.entity.Region;
import com.github.bbijelic.service.config.repository.RegionRepository;
import com.github.bbijelic.service.config.repository.RepositoryException;
import com.scottescue.dropwizard.entitymanager.UnitOfWork;

/**
 * Region resource
 * 
 * @author Bojan Bijelic
 */
@Path("/region")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegionResource {
    
    /**
     * Region repository
     */
    private RegionRepository regionRepository;
    
    /**
     * Constructor
     * 
     * @param regionRepository the region repository
     */
    public RegionResource(final RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Adds new region
     * 
     * @param region the region entity
     */
    @POST
    @UnitOfWork
    public Response addRegion(@Valid Region region){
        
        // Prepare response
        Response response = Response.status(Status.CREATED).build();
        
        try {
            
            // Persist region
            regionRepository.persist(region);
            
        } catch (RepositoryException re){
            
            // Server error response
            response = Response.serverError().build();
        }   
        
        return response;
    }
    
    /**
     * Gets one or list of regions
     */
    @GET
    @UnitOfWork(transactional = false)
    public Response getRegion(@QueryParam("name") Optional<String> nameOptional){
            
        // Prepare response
        Response response = Response.ok().build();
        
        try {
            
            if(nameOptional.isPresent()){
                
                // Get single region identified by the name
                Optional<Region> regionOptional = regionRepository.find(nameOptional.get());
                
                if(regionOptional.isPresent()){
                    
                    // Region found, return it as a entity body
                    response = Response.ok(regionOptional.get()).build();
                    
                } else {
                    // Region not found, return 404
                    response = Response.status(Status.NOT_FOUND).build();
                }
                
            } else {
                
                // Get all the regions                
                List<Region> resultList = regionRepository.getAll();
                // Return it as a entity body
                response = Response.ok(resultList).build();
            }
            
        } catch (RepositoryException re){
            
            // Server error response
            response = Response.serverError().build();
        }   
        
        return response;      
    }
    
    /**
     * Updates existing region
     */
    @PUT
    @UnitOfWork
    public Response updateRegion(
        @NotNull @QueryParam("name") String name, 
        @Valid Region region){
        
        // Prepare response
        Response response = Response.ok().build();
        
        try {
            
            // Find region first
            Optional<Region> regionOptional = regionRepository.find(name);
            
            if(regionOptional.isPresent()){
                
                // Update found region with properties from the request
                Region regionEntity = regionOptional.get();
                regionEntity.setName(region.getName());
                regionEntity.setDescription(region.getDescription());
                
                // Region successfully updated
                response = Response.ok(regionEntity).build();
                
            } else {
                
                // Region not found
                response = Response.status(Status.NOT_FOUND).build();
            }
            
        } catch (RepositoryException re){
            
            // Server error response
            response = Response.serverError().build();
        } 
        
        return response;
    }

    /**
     * Deletes existing region
     */
    @DELETE
    @UnitOfWork
    public Response deleteRegion(@NotNull @QueryParam("name") String name){
            
        // Prepare response
        Response response = Response.noContent().build();
        
        try {
            
            // Find region first
            Optional<Region> regionOptional = regionRepository.find(name);
            
            if(regionOptional.isPresent()){
                
                // Delete the region
                regionRepository.remove(regionOptional.get());
                
                // Region successfully deleted
                response = Response.noContent().build();
                
            } else {
                
                // Region not found
                response = Response.status(Status.NOT_FOUND).build();
            }
            
        } catch (RepositoryException re){
            
            // Server error response
            response = Response.serverError().build();
        } 
        
        return response;
    }
}
