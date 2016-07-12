package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.dp.ardas.radiator.domain.IntegrationTestResult;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the IntegrationTestResult entity.
 */
@SuppressWarnings("unused")
public interface IntegrationTestResultRepository extends JpaRepository<IntegrationTestResult,Long> {

    @Query("select MAX(restResult) from IntegrationTestResult restResult GROUP BY restResult.instancesName")
    List<IntegrationTestResult> findLasts();

    @Query("select MAX(restResult) from IntegrationTestResult restResult " +
            "where restResult.instancesName = :instancesName")
    Optional<IntegrationTestResult> findLastByInstancesName(@Param("instancesName") String instancesName);
}
