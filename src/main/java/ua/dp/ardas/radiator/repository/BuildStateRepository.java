package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.dp.ardas.radiator.domain.BuildState;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the BuildState entity.
 */
@SuppressWarnings("unused")
public interface BuildStateRepository extends JpaRepository<BuildState,Long> {

    Optional<BuildState> findOneByStateAndLastRunTimestemp(String state, Long lastRunTimestemp);

    @Query(value = "" +
            "SELECT bs.ID, bs.INSTANCES_NAME, bs.STATE, bs.ERROR_MESSAGE, bs.LAST_RUN_TIMESTEMP, bs.EXTRACTING_DATE, bs.COMMITER_ID " +
            "FROM BUILD_STATE  bs " +
            "  INNER JOIN (" +
            "    SELECT " +
            "      MAX(ID) as ID," +
            "      MAX(LAST_RUN_TIMESTEMP) " +
            "    FROM BUILD_STATE " +
            "    GROUP BY INSTANCES_NAME) jbs " +
            "  ON bs.ID = jbs.ID", nativeQuery = true)
    List<BuildState> findLastBuildStates();
}
