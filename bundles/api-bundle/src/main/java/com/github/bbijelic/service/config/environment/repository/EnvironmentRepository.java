package com.github.bbijelic.service.config.environment.repository;

import com.github.bbijelic.service.config.core.repository.JpaRepository;
import com.github.bbijelic.service.config.core.repository.RepositoryException;
import com.github.bbijelic.service.config.environment.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class EnvironmentRepository extends JpaRepository<Environment> {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);

    /**
     * Constructor
     *
     * @param type          the type
     * @param entityManager the entity manager
     */
    public EnvironmentRepository(Class<Environment> type, EntityManager entityManager){
        super(type, entityManager);
    }

    /**
     * Find environment by name
     *
     * @param name the environment name
     * @return the optional of environment
     * @throws RepositoryException
     */
    public Optional<Environment> getByName(
            final String name)
            throws RepositoryException{
        Optional<Environment> result = Optional.empty();

        try {

            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Environment> criteriaQuery = criteriaBuilder.createQuery(Environment.class);
            Root<Environment> root = criteriaQuery.from(Environment.class);

            criteriaQuery.select(root);

            ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.where(criteriaBuilder.equal(root.get("name"), nameParameter));

            TypedQuery<Environment> query = getEntityManager().createQuery(criteriaQuery);
            query.setParameter(nameParameter, name);

            result = Optional.ofNullable(query.getSingleResult());

        } catch (NoResultException nre) {
            LOGGER.debug("Query returned no results");

        } catch (Exception e) {
            LOGGER.error("Failed to fetch entity: {} -> {}", e.toString(), e.getStackTrace().toString());
            throw new RepositoryException(e.getMessage(), e);
        }

        return result;
    }
}
