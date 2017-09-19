package com.ballack.com.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ballack on 12/09/2017.
 */
public class Panier implements Serializable {
    private Map<Long, LigneVente> ligneCommandeMap = new HashMap<>();

    public Panier() {
    }

    public void addMedicament(Medicament medicament, int qte) {
        if (ligneCommandeMap.get(medicament.getId()) == null) {
            LigneVente ligneVente = new LigneVente();
            ligneVente.setMedicament(medicament);
            ligneVente.setQuantite(qte);
            ligneVente.setPrix(medicament.getPrix());
        } else {
            LigneVente ligneVente = ligneCommandeMap.get(medicament.getId());
            ligneVente.setQuantite(ligneVente.getQuantite() + qte);

        }
    }

    public Collection<LigneVente> mapTotal() {
        return ligneCommandeMap.values();
    }

    public double getPrixTotal() {
        double t = 0;
        for (LigneVente ligne : ligneCommandeMap.values()) {
            t += ligne.getPrix() * ligne.getQuantite();
        }
        return t;
    }

    public int getNombre() {
        return ligneCommandeMap.size();
    }
}
