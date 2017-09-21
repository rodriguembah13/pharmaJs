package com.ballack.com.web.rest;

import com.ballack.com.domain.LigneVente;
import com.ballack.com.repository.LigneVenteRepository;
import com.ballack.com.service.UserService;
import com.codahale.metrics.annotation.Timed;
import com.ballack.com.domain.Vente;
import com.ballack.com.service.VenteService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Vente.
 */
@RestController
@RequestMapping("/api")
public class VenteResource {

    private final Logger log = LoggerFactory.getLogger(VenteResource.class);

    private static final String ENTITY_NAME = "vente";

    private final VenteService venteService;
    private final UserService userService;
    private final LigneVenteRepository ligneVenteRepository;

    public VenteResource(VenteService venteService, UserService userService, LigneVenteRepository ligneVenteRepository) {
        this.venteService = venteService;
        this.userService = userService;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    /**
     * POST  /ventes : Create a new vente.
     *
     * @param vente the vente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vente, or with status 400 (Bad Request) if the vente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ventes")
    @Timed
    public ResponseEntity<Vente> createVente(@RequestBody Vente vente) throws URISyntaxException {
        log.debug("REST request to save Vente : {}", vente);
        if (vente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vente cannot already have an ID")).body(null);
        }
        Vente result = venteService.save(vente);
        return ResponseEntity.created(new URI("/api/ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /ventes : Create a new vente.
     *
     * @param ligneVentes the vente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vente, or with status 400 (Bad Request) if the vente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ventesLigne")
    @Timed
    public ResponseEntity<Vente> createVenteL(@RequestBody Set<LigneVente> ligneVentes) throws URISyntaxException {
        Vente vente=new Vente();
        Double prix_temp=0.0;
        vente.setDate_vente(LocalDate.now());
        vente.setUser(userService.getUserWithAuthorities());
        vente.setLigneVentes(ligneVentes);
        Vente vente1 = venteService.save(vente);
        for (LigneVente ligneVente:ligneVentes){
            prix_temp=prix_temp+(ligneVente.getMedicament().getPrix()*ligneVente.getQuantite());
            LigneVente ligneVente1=new LigneVente();
            ligneVente1.setVente(vente1);
            ligneVente1.setMedicament(ligneVente.getMedicament());
            ligneVente1.setQuantite(ligneVente.getQuantite());
            ligneVente1.setPrix(ligneVente.getPrix());
            ligneVenteRepository.save(ligneVente1);
            vente.addLigneVente(ligneVente);

        }
        vente.setPrix(prix_temp);
        log.debug("REST request to save Vente : {}", vente);
/*        if (vente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vente cannot already have an ID")).body(null);
        }*/
        Vente result = venteService.save(vente1);
        return ResponseEntity.created(new URI("/api/ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * PUT  /ventes : Updates an existing vente.
     *
     * @param vente the vente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vente,
     * or with status 400 (Bad Request) if the vente is not valid,
     * or with status 500 (Internal Server Error) if the vente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ventes")
    @Timed
    public ResponseEntity<Vente> updateVente(@RequestBody Vente vente) throws URISyntaxException {
        log.debug("REST request to update Vente : {}", vente);
        if (vente.getId() == null) {
            return createVente(vente);
        }
        Vente result = venteService.save(vente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ventes : get all the ventes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ventes in body
     */
    @GetMapping("/ventes")
    @Timed
    public ResponseEntity<List<Vente>> getAllVentes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ventes");
        Page<Vente> page = venteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ventes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ventes/:id : get the "id" vente.
     *
     * @param id the id of the vente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vente, or with status 404 (Not Found)
     */
    @GetMapping("/ventes/{id}")
    @Timed
    public ResponseEntity<Vente> getVente(@PathVariable Long id) {
        log.debug("REST request to get Vente : {}", id);
        Vente vente = venteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vente));
    }

    /**
     * DELETE  /ventes/:id : delete the "id" vente.
     *
     * @param id the id of the vente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ventes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        log.debug("REST request to delete Vente : {}", id);
        venteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
