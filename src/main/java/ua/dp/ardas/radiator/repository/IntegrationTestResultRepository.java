package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.dp.ardas.radiator.domain.IntegrationTestResult;

import java.util.Optional;

/**
 * Spring Data JPA repository for the IntegrationTestResult entity.
 */
@SuppressWarnings("unused")
public interface IntegrationTestResultRepository extends JpaRepository<IntegrationTestResult,Long> {

    @Query("select MAX(result) from IntegrationTestResult result")
    Optional<IntegrationTestResult> findLast();
}
