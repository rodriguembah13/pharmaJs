package com.ballack.com.repository;

import com.ballack.com.domain.Medicament;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medicament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

}
