package com.ballack.com.service;

import com.ballack.com.domain.Vente;
import com.ballack.com.repository.VenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Vente.
 */
@Service
@Transactional
public class VenteService {

    private final Logger log = LoggerFactory.getLogger(VenteService.class);

    private final VenteRepository venteRepository;
    public VenteService(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
    }

    /**
     * Save a vente.
     *
     * @param vente the entity to save
     * @return the persisted entity
     */
    public Vente save(Vente vente) {
        log.debug("Request to save Vente : {}", vente);
        return venteRepository.save(vente);
    }

    /**
     *  Get all the ventes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Vente> findAll(Pageable pageable) {
        log.debug("Request to get all Ventes");
        return venteRepository.findAll(pageable);
    }

    /**
     *  Get one vente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Vente findOne(Long id) {
        log.debug("Request to get Vente : {}", id);
        return venteRepository.findOne(id);
    }

    /**
     *  Delete the  vente by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vente : {}", id);
        venteRepository.delete(id);
    }
}
