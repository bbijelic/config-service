package com.github.bbijelic.service.config.core.exception;

/**
 * System exception
 * 
 * @author Bojan Bijelic
 */
public class SystemException extends Exception {
    
    private static final long serialVersionUID = 0L;
    
    /**
     * Constructor
     * 
     * @param message the exception message
     * @param cause the exception cause
     */
    public SystemException(final String message, final Throwable cause){
        super(message, cause);
    }
    
    /**
     * Constructor
     * 
     * @param message the exception message
     */
    public SystemException(final String message){
        super(message);
    }
    
    /**
     * Constructor
     * 
     * @param cause the exception cause
     */
    public SystemException(final Throwable cause){
        super(cause);
    }
}
