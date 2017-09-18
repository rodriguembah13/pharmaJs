package com.ballack.com.repository;

import com.ballack.com.domain.Medicament;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Medicament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    @Query("select distinct medicament from Medicament medicament left join fetch medicament.stocks")
    List<Medicament> findAllWithEagerRelationships();

    @Query("select medicament from Medicament medicament left join fetch medicament.stocks where medicament.id =:id")
    Medicament findOneWithEagerRelationships(@Param("id") Long id);

}
