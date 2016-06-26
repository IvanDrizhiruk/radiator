package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;

/**
 * Spring Data JPA repository for the KanbanFlowBoard entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowBoardRepository extends JpaRepository<KanbanFlowBoard,Long> {

    KanbanFlowBoard findOneByName(String name);
}
