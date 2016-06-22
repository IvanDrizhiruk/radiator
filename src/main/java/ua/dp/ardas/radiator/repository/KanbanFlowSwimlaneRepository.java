package ua.dp.ardas.radiator.repository;

import ua.dp.ardas.radiator.domain.KanbanFlowSwimlane;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KanbanFlowSwimlane entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowSwimlaneRepository extends JpaRepository<KanbanFlowSwimlane,Long> {

}
