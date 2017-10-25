package com.github.bbijelic.service.config.application.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bbijelic.service.config.application.api.Application;
import com.github.bbijelic.service.config.core.repository.JpaRepository;
import com.github.bbijelic.service.config.core.repository.RepositoryException;

/**
 * Application repository
 */
public class ApplicationRepository extends JpaRepository<Application> {
    
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRepository.class);
    
    /**
     * Constructor
     * 
     * @param type the entity type
     * @param entityManager the entity manager
     */
    public ApplicationRepository(Class<Application> type, final EntityManager entityManager) {
        super(type, entityManager);
    }
    
    /**
	 * Find application by name
	 * 
	 * @param name
	 *            the application name
	 * @param offset the offset
	 * @param limit the limit
	 * @param sortBy
	 * 
	 * @return the optional of application
	 * @throws RepositoryException
	 */
	public Optional<Application> getByName(
        final String name) 
            throws RepositoryException {
	    Optional<Application> result = Optional.empty();
	    	    
	    try {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
			Root<Application> root = criteriaQuery.from(Application.class);
			
			criteriaQuery.select(root);

			ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get("name"), nameParameter));
		        
			TypedQuery<Application> query = getEntityManager().createQuery(criteriaQuery);
			query.setParameter(nameParameter, name);
						
			result = Optional.ofNullable(query.getSingleResult());
						
		} catch (NoResultException nre) {
			LOGGER.debug("Query returned no results");

		} catch (Exception e) {
			LOGGER.error(e.toString());
			LOGGER.error("Getting region failed: {} -> {}", e.toString(), e.getStackTrace().toString() );
			throw new RepositoryException(e.getMessage(), e);
		}
	    
	    return result;
	}
    
}
