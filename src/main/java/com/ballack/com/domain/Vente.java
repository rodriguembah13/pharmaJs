package com.ballack.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vente.
 */
@Entity
@Table(name = "vente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_vente")
    private LocalDate date_vente;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "total")
    private String total;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "vente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LigneVente> ligneVentes = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_vente() {
        return date_vente;
    }

    public Vente date_vente(LocalDate date_vente) {
        this.date_vente = date_vente;
        return this;
    }

    public void setDate_vente(LocalDate date_vente) {
        this.date_vente = date_vente;
    }

    public Double getPrix() {
        return prix;
    }

    public Vente prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getTotal() {
        return total;
    }

    public Vente total(String total) {
        this.total = total;
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public Vente client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<LigneVente> getLigneVentes() {
        return ligneVentes;
    }

    public Vente ligneVentes(Set<LigneVente> ligneVentes) {
        this.ligneVentes = ligneVentes;
        return this;
    }

    public Vente addLigneVente(LigneVente ligneVente) {
        this.ligneVentes.add(ligneVente);
        ligneVente.setVente(this);
        return this;
    }

    public Vente removeLigneVente(LigneVente ligneVente) {
        this.ligneVentes.remove(ligneVente);
        ligneVente.setVente(null);
        return this;
    }

    public void setLigneVentes(Set<LigneVente> ligneVentes) {
        this.ligneVentes = ligneVentes;
    }

    public User getUser() {
        return user;
    }

    public Vente user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Vente vente = (Vente) o;
        if (vente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vente{" +
            "id=" + getId() +
            ", date_vente='" + getDate_vente() + "'" +
            ", prix='" + getPrix() + "'" +
            ", total='" + getTotal() + "'" +
            "}";
    }
}
