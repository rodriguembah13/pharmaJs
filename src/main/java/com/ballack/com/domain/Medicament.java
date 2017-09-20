package com.ballack.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Medicament.
 */
@Entity
@Table(name = "medicament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medicament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix")
    private Double prix;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "code")
    private String code;

    @ManyToOne
    private FamilleMedicament familleMedicament;

    @OneToOne
    @JoinColumn(unique = true)
    private Stock stock;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Medicament libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public Medicament prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public byte[] getImage() {
        return image;
    }

    public Medicament image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Medicament imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getCode() {
        return code;
    }

    public Medicament code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FamilleMedicament getFamilleMedicament() {
        return familleMedicament;
    }

    public Medicament familleMedicament(FamilleMedicament familleMedicament) {
        this.familleMedicament = familleMedicament;
        return this;
    }

    public void setFamilleMedicament(FamilleMedicament familleMedicament) {
        this.familleMedicament = familleMedicament;
    }

    public Stock getStock() {
        return stock;
    }

    public Medicament stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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
        Medicament medicament = (Medicament) o;
        if (medicament.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicament.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medicament{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prix='" + getPrix() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
