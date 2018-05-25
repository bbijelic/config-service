package com.github.bbijelic.service.config.configuration.file.repository;

import com.github.bbijelic.service.config.application.api.Application;
import com.github.bbijelic.service.config.configuration.file.api.FileConfiguration;
import com.github.bbijelic.service.config.core.repository.JpaRepository;
import com.github.bbijelic.service.config.core.repository.RepositoryException;
import com.github.bbijelic.service.config.environment.api.Environment;
import com.github.bbijelic.service.config.region.api.Region;
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

/**
 * File configuration repository
 *
 * @author Bojan Bijelić
 * @since 1.0.0
 */
public class FileConfigurationRepository extends JpaRepository<FileConfiguration> {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileConfigurationRepository.class);

    /**
     * Constructor
     *
     * @param type
     * @param entityManager
     */
    public FileConfigurationRepository(Class<FileConfiguration> type, EntityManager entityManager){
        super(type, entityManager);
    }

    /**
     * Finds exact file configuration identified by application, environment, region and name
     *
     * @param application the application
     * @param environment the environment
     * @param region      the region
     * @param name        the name
     * @return the file configuration optional
     * @throws RepositoryException
     */
    public Optional<FileConfiguration> findExact(
            final Application application,
            final Environment environment,
            final Region region,
            final String name)
            throws RepositoryException{

        Optional<FileConfiguration> result = Optional.empty();

        try {

            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<FileConfiguration> criteriaQuery = criteriaBuilder.createQuery(FileConfiguration.class);
            Root<FileConfiguration> root = criteriaQuery.from(FileConfiguration.class);

            criteriaQuery.select(root);

            // Parameters
            ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
            ParameterExpression<Application> applicationParameter = criteriaBuilder.parameter(Application.class);
            ParameterExpression<Environment> environmentParameter = criteriaBuilder.parameter(Environment.class);
            ParameterExpression<Region> regionParameter = criteriaBuilder.parameter(Region.class);

            criteriaQuery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("application"), applicationParameter),
                    criteriaBuilder.equal(root.get("environment"), environmentParameter),
                    criteriaBuilder.equal(root.get("region"), regionParameter),
                    criteriaBuilder.equal(root.get("name"), nameParameter)
            ));

            TypedQuery<FileConfiguration> query = getEntityManager().createQuery(criteriaQuery);
            query.setParameter(nameParameter, name);
            query.setParameter(applicationParameter, application);
            query.setParameter(environmentParameter, environment);
            query.setParameter(regionParameter, region);

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
