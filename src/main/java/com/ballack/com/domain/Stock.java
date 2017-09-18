package com.ballack.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "alert_quantite")
    private Integer alertQuantite;

    @Column(name = "libelle")
    private String libelle;

    @ManyToMany(mappedBy = "stocks")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Medicament> medicaments = new HashSet<>();

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

    public Stock quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getAlertQuantite() {
        return alertQuantite;
    }

    public Stock alertQuantite(Integer alertQuantite) {
        this.alertQuantite = alertQuantite;
        return this;
    }

    public void setAlertQuantite(Integer alertQuantite) {
        this.alertQuantite = alertQuantite;
    }

    public String getLibelle() {
        return libelle;
    }

    public Stock libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public Stock medicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
        return this;
    }

    public Stock addMedicament(Medicament medicament) {
        this.medicaments.add(medicament);
        medicament.getStocks().add(this);
        return this;
    }

    public Stock removeMedicament(Medicament medicament) {
        this.medicaments.remove(medicament);
        medicament.getStocks().remove(this);
        return this;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
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
        Stock stock = (Stock) o;
        if (stock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", quantite='" + getQuantite() + "'" +
            ", alertQuantite='" + getAlertQuantite() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
