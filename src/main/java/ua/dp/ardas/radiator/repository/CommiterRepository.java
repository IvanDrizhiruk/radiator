package ua.dp.ardas.radiator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ardas.radiator.domain.Commiter;

/**
 * Spring Data JPA repository for the Commiter entity.
 */
@SuppressWarnings("unused")
public interface CommiterRepository extends JpaRepository<Commiter,Long> {

    Commiter findOneByEmail(String email);
}
