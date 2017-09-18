package com.ballack.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ballack.com.domain.FamilleMedicament;

import com.ballack.com.repository.FamilleMedicamentRepository;
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
 * REST controller for managing FamilleMedicament.
 */
@RestController
@RequestMapping("/api")
public class FamilleMedicamentResource {

    private final Logger log = LoggerFactory.getLogger(FamilleMedicamentResource.class);

    private static final String ENTITY_NAME = "familleMedicament";

    private final FamilleMedicamentRepository familleMedicamentRepository;
    public FamilleMedicamentResource(FamilleMedicamentRepository familleMedicamentRepository) {
        this.familleMedicamentRepository = familleMedicamentRepository;
    }

    /**
     * POST  /famille-medicaments : Create a new familleMedicament.
     *
     * @param familleMedicament the familleMedicament to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familleMedicament, or with status 400 (Bad Request) if the familleMedicament has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/famille-medicaments")
    @Timed
    public ResponseEntity<FamilleMedicament> createFamilleMedicament(@RequestBody FamilleMedicament familleMedicament) throws URISyntaxException {
        log.debug("REST request to save FamilleMedicament : {}", familleMedicament);
        if (familleMedicament.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new familleMedicament cannot already have an ID")).body(null);
        }
        FamilleMedicament result = familleMedicamentRepository.save(familleMedicament);
        return ResponseEntity.created(new URI("/api/famille-medicaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /famille-medicaments : Updates an existing familleMedicament.
     *
     * @param familleMedicament the familleMedicament to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familleMedicament,
     * or with status 400 (Bad Request) if the familleMedicament is not valid,
     * or with status 500 (Internal Server Error) if the familleMedicament couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/famille-medicaments")
    @Timed
    public ResponseEntity<FamilleMedicament> updateFamilleMedicament(@RequestBody FamilleMedicament familleMedicament) throws URISyntaxException {
        log.debug("REST request to update FamilleMedicament : {}", familleMedicament);
        if (familleMedicament.getId() == null) {
            return createFamilleMedicament(familleMedicament);
        }
        FamilleMedicament result = familleMedicamentRepository.save(familleMedicament);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familleMedicament.getId().toString()))
            .body(result);
    }

    /**
     * GET  /famille-medicaments : get all the familleMedicaments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of familleMedicaments in body
     */
    @GetMapping("/famille-medicaments")
    @Timed
    public ResponseEntity<List<FamilleMedicament>> getAllFamilleMedicaments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FamilleMedicaments");
        Page<FamilleMedicament> page = familleMedicamentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/famille-medicaments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /famille-medicaments/:id : get the "id" familleMedicament.
     *
     * @param id the id of the familleMedicament to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familleMedicament, or with status 404 (Not Found)
     */
    @GetMapping("/famille-medicaments/{id}")
    @Timed
    public ResponseEntity<FamilleMedicament> getFamilleMedicament(@PathVariable Long id) {
        log.debug("REST request to get FamilleMedicament : {}", id);
        FamilleMedicament familleMedicament = familleMedicamentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(familleMedicament));
    }

    /**
     * DELETE  /famille-medicaments/:id : delete the "id" familleMedicament.
     *
     * @param id the id of the familleMedicament to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/famille-medicaments/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamilleMedicament(@PathVariable Long id) {
        log.debug("REST request to delete FamilleMedicament : {}", id);
        familleMedicamentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
