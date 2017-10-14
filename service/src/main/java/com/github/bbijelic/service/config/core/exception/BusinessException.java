package com.github.bbijelic.service.config.core.exception;

/**
 * Business exception
 * 
 * @author Bojan Bijelic
 */
public class BusinessException extends Exception {
    
    private static final long serialVersionUID = 0L;
    
    /**
     * Constructor
     * 
     * @param message the exception message
     * @param cause the exception cause
     */
    public BusinessException(final String message, final Throwable cause){
        super(message, cause);
    }
    
    /**
     * Constructor
     * 
     * @param message the exception message
     */
    public BusinessException(final String message){
        super(message);
    }
    
    /**
     * Constructor
     * 
     * @param cause the exception cause
     */
    public BusinessException(final Throwable cause){
        super(cause);
    }
    
}
