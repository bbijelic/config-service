package com.github.bbijelic.service.config.core.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract JPA repository
 *
 * @author Bojan Bijelic
 */
public class JpaRepository<T> implements Repository<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepository.class);

	/**
	 * Entity manager
	 */
	private EntityManager entityManager;

	/**
	 * Type
	 */
	private Class<T> type;

	/**
	 * Constructor
	 * 
	 * @param entityManager
	 *            the entity manager
	 */
	public JpaRepository(Class<T> type, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.type = type;
	}

	/**
	 * Entity manager getter
	 * 
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public Optional<T> get(long id) throws RepositoryException {
		LOGGER.debug("Enter: get(id={})", id);

		// Initialize optional
		Optional<T> entity = Optional.empty();

		try {

			// Fetch entity from database
			entity = Optional.ofNullable(entityManager.find(type, id));

		} catch (NoResultException nre) {
			LOGGER.debug("Query returned no results");

		} catch (Throwable t) {

			LOGGER.error("Getting entity by ID failed: {}", t.getMessage());
			throw new RepositoryException(t.getMessage(), t);
		}

		LOGGER.debug("Leaving: get(id={}), returning={}", id, entity.toString());
		return entity;
	}

	@Override
	public List<T> getAll(
        final Optional<Integer> offsetOptional, 
        final Optional<Integer> limitOptional, 
        final Optional<String> sortByOptional, 
        final Optional<String> sortOrderOptional) 
            throws RepositoryException {
		LOGGER.debug("Enter: getAll()");

		// Initialize optional
		List<T> resultList = new ArrayList<>();

		try {

			final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			final CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);
			
			final Root<T> root = criteria.from(type);
			criteria.select(root);
			
			// Configure ordering
			configureOrdering(criteriaBuilder, criteria, root, sortByOptional, sortOrderOptional);

			final TypedQuery<T> query = entityManager.createQuery(criteria);
            
            // Configure pagination
            configurePagination(query, offsetOptional, limitOptional);

			resultList = query.getResultList();

		} catch (NoResultException nre) {
			LOGGER.debug("Query returned no results");

		} catch (Throwable t) {
			LOGGER.error("Getting all entities failed: {}", t.getMessage());
			throw new RepositoryException(t.getMessage(), t);
		}

		LOGGER.debug("Leaving: getAll(), returning={}", resultList.toString());
		return resultList;
	}
	
	/**
	 * Configures ordering
	 * 
	 * @param criteriaBuilder the criteria builder
	 * @param criteriaQuery the criteria query
	 * @param root the root
	 * @param sortByOptional the sort by optional field
	 * @param sortOrderOptional the sort order optional
	 */
	protected void configureOrdering(
        final CriteriaBuilder criteriaBuilder,
        final CriteriaQuery<T> criteriaQuery, 
        final Root<T> root,
        final Optional<String> sortByOptional, 
        final Optional<String> sortOrderOptional){
        // When sorting is defined
	    if(sortByOptional.isPresent()){
	        Order order = criteriaBuilder.asc(root.get(sortByOptional.get()));
	        if(sortOrderOptional.isPresent()){
	            if(sortOrderOptional.get().equalsIgnoreCase("desc")){
	                order = criteriaBuilder.desc(root.get(sortByOptional.get()));
	            }
	        }
	        // Set ordering
	        criteriaQuery.orderBy(order);
	    }
    }
    
    /**
     * Configures pagination
     * 
     * @param query the query
     * @param offsetOptional the offset optional
     * @param limitOptional the limit optional
     */
    protected void configurePagination(
        final Query query,
        final Optional<Integer> offsetOptional, 
        final Optional<Integer> limitOptional){
            
        // Set offset and limit if present
		if(offsetOptional.isPresent()) query.setFirstResult(offsetOptional.get());
		if(limitOptional.isPresent()) query.setMaxResults(limitOptional.get());
    }

	@Override
	public void persist(T entity) throws RepositoryException {
		LOGGER.debug("Entering: persist(entity={})", entity.toString());

		try {

			// Persist entity
			entityManager.persist(entity);

        } catch (ConstraintViolationException cve) {
            LOGGER.error("Persisting entity failed: {}", cve.getMessage());
			throw new RepositoryException(cve.getMessage(), cve);

		} catch (Throwable t) {
		    
			LOGGER.error("Persisting entity failed: {}", t.getMessage());
			throw new RepositoryException(t.getMessage(), t);
		}

		LOGGER.debug("Leaving: persist(entity={})", entity.toString());
	}

	@Override
	public void remove(T entity) throws RepositoryException {
		LOGGER.debug("Entering: remove(entity={})", entity.toString());

		try {

			// Merge entity
			entityManager.remove(entity);

		} catch (Throwable t) {

			LOGGER.error("Removing entity failed: {}", t.getMessage());
			throw new RepositoryException(t.getMessage(), t);
		}

		LOGGER.debug("Leaving: remove(entity={})", entity.toString());
	}
}