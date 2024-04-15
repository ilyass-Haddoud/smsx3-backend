package com.jwt.api.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwt.api.supplier.Supplier;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String siteVente;
    private String type;
    private String numeroFacture;
    private String reference;
    private Date date;
    private String clientFacture;
    private String clientIntitule;
    private String clientCommande;
    private String tiersPayeur;
    private String clientGroupe;
    private String etat;
    private String devise;
    private Date debutEcheance;
    private String typePaiement;
    private Date dateDebutPeriode;
    private Date dateFinPeriode;
    private String document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties("invoices")
    private Supplier supplier;

    public Invoice() {
    }

    public Integer getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteVente() {
        return siteVente;
    }

    public void setSiteVente(String siteVente) {
        this.siteVente = siteVente;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClientFacture() {
        return clientFacture;
    }

    public void setClientFacture(String clientFacture) {
        this.clientFacture = clientFacture;
    }

    public String getClientIntitule() {
        return clientIntitule;
    }

    public void setClientIntitule(String clientIntitule) {
        this.clientIntitule = clientIntitule;
    }

    public String getClientCommande() {
        return clientCommande;
    }

    public void setClientCommande(String clientCommande) {
        this.clientCommande = clientCommande;
    }

    public String getTiersPayeur() {
        return tiersPayeur;
    }

    public void setTiersPayeur(String tiersPayeur) {
        this.tiersPayeur = tiersPayeur;
    }

    public String getClientGroupe() {
        return clientGroupe;
    }

    public void setClientGroupe(String clientGroupe) {
        this.clientGroupe = clientGroupe;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Date getDebutEcheance() {
        return debutEcheance;
    }

    public void setDebutEcheance(Date debutEcheance) {
        this.debutEcheance = debutEcheance;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public Date getDateDebutPeriode() {
        return dateDebutPeriode;
    }

    public void setDateDebutPeriode(Date dateDebutPeriode) {
        this.dateDebutPeriode = dateDebutPeriode;
    }

    public Date getDateFinPeriode() {
        return dateFinPeriode;
    }

    public void setDateFinPeriode(Date dateFinPeriode) {
        this.dateFinPeriode = dateFinPeriode;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
