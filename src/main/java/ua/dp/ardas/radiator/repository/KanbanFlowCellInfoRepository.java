package ua.dp.ardas.radiator.repository;

import ua.dp.ardas.radiator.domain.KanbanFlowCellInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KanbanFlowCellInfo entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowCellInfoRepository extends JpaRepository<KanbanFlowCellInfo,Long> {

}
