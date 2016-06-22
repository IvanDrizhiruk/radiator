package ua.dp.ardas.radiator.repository;

import ua.dp.ardas.radiator.domain.KanbanFlowColumn;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KanbanFlowColumn entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowColumnRepository extends JpaRepository<KanbanFlowColumn,Long> {

}
