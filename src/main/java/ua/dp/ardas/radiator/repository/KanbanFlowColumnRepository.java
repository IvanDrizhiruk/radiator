package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ardas.radiator.domain.KanbanFlowColumn;

/**
 * Spring Data JPA repository for the KanbanFlowColumn entity.
 */
@SuppressWarnings("unused")
public interface KanbanFlowColumnRepository extends JpaRepository<KanbanFlowColumn,Long> {

    KanbanFlowColumn findOneByNameAndIndexNumber(String name, Integer indexNumber);
}
