package com.ballack.com.repository;

import com.ballack.com.domain.LigneVente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LigneVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

}
