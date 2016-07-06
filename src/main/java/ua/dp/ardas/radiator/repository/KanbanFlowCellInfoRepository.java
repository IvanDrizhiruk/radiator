package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.dp.ardas.radiator.domain.KanbanFlowBoard;
import ua.dp.ardas.radiator.domain.KanbanFlowCellInfo;

import java.util.List;

/**
 * Spring Data JPA repository for the KanbanFlowCellInfo entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowCellInfoRepository extends JpaRepository<KanbanFlowCellInfo,Long> {

    @Query("select MAX(cell) from KanbanFlowCellInfo cell WHERE cell.board = :boardName GROUP BY cell.column, cell.swimlane")
    List<KanbanFlowCellInfo> findLastKanbanFlowCells(@Param("boardName") KanbanFlowBoard boardName);
}
