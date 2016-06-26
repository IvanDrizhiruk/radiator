package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.domain.KanbanFlowColumn;

/**
 * Spring Data JPA repository for the KanbanFlowColumn entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowColumnRepository extends JpaRepository<KanbanFlowColumn,Long> {

    KanbanFlowColumn findOneByNameAndIndexNumberAndBoard(String name, Integer indexNumber, KanbanFlowBoard board);
}
