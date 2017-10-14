package com.github.bbijelic.service.config.repository;

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

import com.github.bbijelic.service.config.model.Region;

/**
 * Region repository
 */
public class RegionRepository extends JpaRepository<Region> {
    
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionRepository.class);
    
    /**
     * Constructor
     * 
     * @param type the entity type
     * @param entityManager the entity manager
     */
    public RegionRepository(Class<Region> type, EntityManager entityManager) {
        super(type, entityManager);
    }
    
    /**
	 * Find region by name
	 * 
	 * @param name
	 *            the region name
	 * @return the optional of region
	 * @throws RepositoryException
	 */
	public Optional<Region> find(final String name) throws RepositoryException {
	    Optional<Region> result = Optional.empty();
	    
	    try {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Region> criteriaQuery = criteriaBuilder.createQuery(Region.class);
			Root<Region> root = criteriaQuery.from(Region.class);
			
			criteriaQuery.select(root);

			ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get("name"), nameParameter));
			
			TypedQuery<Region> query = getEntityManager().createQuery(criteriaQuery);
			query.setParameter(nameParameter, name);
			
			result = Optional.ofNullable(query.getSingleResult());
						
		} catch (NoResultException nre) {
			LOGGER.debug("Query returned no results");

		} catch (Throwable t) {
			
			LOGGER.error("Getting torrent failed: {}", t.toString());
			throw new RepositoryException(t.getMessage(), t);
		}
	    
	    return result;
	}
    
}
