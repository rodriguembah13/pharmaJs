package com.ballack.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LigneVente.
 */
@Entity
@Table(name = "ligne_vente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LigneVente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "prix")
    private Double prix;

    @ManyToOne
    private Medicament medicament;

    @ManyToOne
    private Vente vente;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public LigneVente quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrix() {
        return prix;
    }

    public LigneVente prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public LigneVente medicament(Medicament medicament) {
        this.medicament = medicament;
        return this;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Vente getVente() {
        return vente;
    }

    public LigneVente vente(Vente vente) {
        this.vente = vente;
        return this;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LigneVente ligneVente = (LigneVente) o;
        if (ligneVente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ligneVente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LigneVente{" +
            "id=" + getId() +
            ", quantite='" + getQuantite() + "'" +
            ", prix='" + getPrix() + "'" +
            "}";
    }
}
