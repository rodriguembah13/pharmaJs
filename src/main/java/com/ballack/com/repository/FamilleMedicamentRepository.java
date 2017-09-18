package com.ballack.com.repository;

import com.ballack.com.domain.FamilleMedicament;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FamilleMedicament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilleMedicamentRepository extends JpaRepository<FamilleMedicament, Long> {

}
