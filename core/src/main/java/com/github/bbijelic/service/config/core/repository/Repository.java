package com.github.bbijelic.service.config.core.repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository
 * 
 * @author Bojan Bijelic
 */
public interface Repository<T> {

	/**
	 * Gets entity by id
	 * 
	 * @param id
	 *            the entity id
	 * @return the optional of entity
	 * @throws JpaException
	 */
	public Optional<T> get(final long id) throws RepositoryException;

	/**
	 * Gets all entities
	 * 
	 * @param offsetOptional the offset optional
	 * @param limitOptional the limit optional
	 * @param sortByOptional the sort by optional
	 * @param sortOrderOptional the sort order optional
	 * 
	 * @return the list of entities
	 * @throws JpaException
	 */
	public List<T> getAll(
        final Optional<Integer> offsetOptional, 
        final Optional<Integer> limitOptional, 
        final Optional<String> sortByOptional, 
        final Optional<String> sortOrderOptional) throws RepositoryException;

	/**
	 * Persists the entity by merging
	 * @param entity the entity
	 */
	public void persist(T entity) throws RepositoryException;
	
	/**
	 * Removes entity 
	 * @param entity the entity
	 */
	public void remove(T entity) throws RepositoryException;
}