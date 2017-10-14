package com.github.bbijelic.service.config.repository;

/**
 * Repository exception
 * 
 * @author Bojan Bijelić
 */
public class RepositoryException extends Exception {
    
    private static final long serialVersionUID = -7868061882689703519L;
    
    /**
	 * Constructor
	 * 
	 * @param message
	 *            the message
	 */
	public RepositoryException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}
    
}
