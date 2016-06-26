package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.domain.KanbanFlowSwimlane;

/**
 * Spring Data JPA repository for the KanbanFlowSwimlane entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowSwimlaneRepository extends JpaRepository<KanbanFlowSwimlane,Long> {

    KanbanFlowSwimlane findOneByNameAndIndexNumberAndBoard(String name, Integer indexNumber, KanbanFlowBoard board);
}
