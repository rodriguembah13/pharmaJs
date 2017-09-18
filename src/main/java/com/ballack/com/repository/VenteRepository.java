package com.ballack.com.repository;

import com.ballack.com.domain.Vente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Vente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("select vente from Vente vente where vente.user.login = ?#{principal.username}")
    List<Vente> findByUserIsCurrentUser();

}
