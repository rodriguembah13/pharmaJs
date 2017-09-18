package com.ballack.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ballack.com.domain.LigneVente;

import com.ballack.com.repository.LigneVenteRepository;
import com.ballack.com.web.rest.util.HeaderUtil;
import com.ballack.com.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LigneVente.
 */
@RestController
@RequestMapping("/api")
public class LigneVenteResource {

    private final Logger log = LoggerFactory.getLogger(LigneVenteResource.class);

    private static final String ENTITY_NAME = "ligneVente";

    private final LigneVenteRepository ligneVenteRepository;
    public LigneVenteResource(LigneVenteRepository ligneVenteRepository) {
        this.ligneVenteRepository = ligneVenteRepository;
    }

    /**
     * POST  /ligne-ventes : Create a new ligneVente.
     *
     * @param ligneVente the ligneVente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ligneVente, or with status 400 (Bad Request) if the ligneVente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ligne-ventes")
    @Timed
    public ResponseEntity<LigneVente> createLigneVente(@RequestBody LigneVente ligneVente) throws URISyntaxException {
        log.debug("REST request to save LigneVente : {}", ligneVente);
        if (ligneVente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ligneVente cannot already have an ID")).body(null);
        }
        LigneVente result = ligneVenteRepository.save(ligneVente);
        return ResponseEntity.created(new URI("/api/ligne-ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ligne-ventes : Updates an existing ligneVente.
     *
     * @param ligneVente the ligneVente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ligneVente,
     * or with status 400 (Bad Request) if the ligneVente is not valid,
     * or with status 500 (Internal Server Error) if the ligneVente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ligne-ventes")
    @Timed
    public ResponseEntity<LigneVente> updateLigneVente(@RequestBody LigneVente ligneVente) throws URISyntaxException {
        log.debug("REST request to update LigneVente : {}", ligneVente);
        if (ligneVente.getId() == null) {
            return createLigneVente(ligneVente);
        }
        LigneVente result = ligneVenteRepository.save(ligneVente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ligneVente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ligne-ventes : get all the ligneVentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ligneVentes in body
     */
    @GetMapping("/ligne-ventes")
    @Timed
    public ResponseEntity<List<LigneVente>> getAllLigneVentes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LigneVentes");
        Page<LigneVente> page = ligneVenteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ligne-ventes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ligne-ventes/:id : get the "id" ligneVente.
     *
     * @param id the id of the ligneVente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ligneVente, or with status 404 (Not Found)
     */
    @GetMapping("/ligne-ventes/{id}")
    @Timed
    public ResponseEntity<LigneVente> getLigneVente(@PathVariable Long id) {
        log.debug("REST request to get LigneVente : {}", id);
        LigneVente ligneVente = ligneVenteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ligneVente));
    }

    /**
     * DELETE  /ligne-ventes/:id : delete the "id" ligneVente.
     *
     * @param id the id of the ligneVente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ligne-ventes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLigneVente(@PathVariable Long id) {
        log.debug("REST request to delete LigneVente : {}", id);
        ligneVenteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
