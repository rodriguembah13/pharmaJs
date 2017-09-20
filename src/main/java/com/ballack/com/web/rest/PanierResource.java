package com.ballack.com.web.rest;

import com.ballack.com.domain.LigneVente;
import com.ballack.com.domain.Medicament;
import com.ballack.com.domain.Panier;
import com.ballack.com.domain.Stock;
import com.ballack.com.repository.LigneVenteRepository;
import com.ballack.com.repository.MedicamentRepository;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;

/**
 * Created by ballack on 15/09/2017.
 */
@RestController
@RequestMapping("/api")
public class PanierResource {
    private final LigneVenteRepository ligneVenteRepository;
    private final MedicamentRepository medicamentRepository;
        private Panier panier=new Panier();
    public PanierResource(LigneVenteRepository ligneVenteRepository, MedicamentRepository medicamentRepository) {
        this.ligneVenteRepository = ligneVenteRepository;
        this.medicamentRepository = medicamentRepository;
    }
    @GetMapping("/addPanier/{id}/{qte}")
    @Timed
    public void addPanier(@PathVariable Long id,@PathVariable int qte) {
        Medicament medicament=medicamentRepository.findOne(id);

        }
    @GetMapping("/viewPanier")
    @Timed
    public Collection <LigneVente> voirPanier() {
        Collection <LigneVente> ligneVentes;
             ligneVentes= panier.mapTotal();
        return  ligneVentes;
    }
}
