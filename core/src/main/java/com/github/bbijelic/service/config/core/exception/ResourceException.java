package com.github.bbijelic.service.config.core.exception;

/**
 * Resource exception
 * 
 * @author Bojan Bijelic
 */
public class ResourceException extends Exception {
    
    private static final long serialVersionUID = 0L;
    
    /**
     * Constructor
     * 
     * @param message the exception message
     * @param cause the exception cause
     */
    public ResourceException(final String message, final Throwable cause){
        super(message, cause);
    }
    
    /**
     * Constructor
     * 
     * @param message the exception message
     */
    public ResourceException(final String message){
        super(message);
    }
    
    /**
     * Constructor
     * 
     * @param cause the exception cause
     */
    public ResourceException(final Throwable cause){
        super(cause);
    }
    
}
