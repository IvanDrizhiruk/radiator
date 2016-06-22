package ua.dp.ardas.radiator.repository;

import ua.dp.ardas.radiator.domain.KanbanFlowBoard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KanbanFlowBoard entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowBoardRepository extends JpaRepository<KanbanFlowBoard,Long> {

}
