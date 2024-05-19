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
    private String site_facturation;
    private String type_facture;
    private String numero_piece;
    private Date date_comptable;
    private String fournisseur;
    private String raison_sociale;
    private String bon_a_payer;
    private Date date_facturation;
    private String tiers_paye;
    private String facture_origine;
    private String ville;
    private Date premiere_echeance;
    private String condition_paiement;
    private String regime_taxe;
    private String total_ht;
    private String total_taxe;
    private String total_ttc;
    private String etat;

    @ManyToOne
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

    public Date getDate_facturation() {
        return date_facturation;
    }

    public void setDate_facturation(Date date_facturation) {
        this.date_facturation = date_facturation;
    }

    public String getSite_facturation() {
        return site_facturation;
    }

    public void setSite_facturation(String site_facturation) {
        this.site_facturation = site_facturation;
    }

    public String getType_facture() {
        return type_facture;
    }

    public void setType_facture(String type_facture) {
        this.type_facture = type_facture;
    }

    public String getNumero_piece() {
        return numero_piece;
    }

    public void setNumero_piece(String numero_piece) {
        this.numero_piece = numero_piece;
    }

    public Date getDate_comptable() {
        return date_comptable;
    }

    public void setDate_comptable(Date date_comptable) {
        this.date_comptable = date_comptable;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getRaison_sociale() {
        return raison_sociale;
    }

    public void setRaison_sociale(String raison_sociale) {
        this.raison_sociale = raison_sociale;
    }

    public String getBon_a_payer() {
        return bon_a_payer;
    }

    public void setBon_a_payer(String bon_a_payer) {
        this.bon_a_payer = bon_a_payer;
    }

    public String getTiers_paye() {
        return tiers_paye;
    }

    public void setTiers_paye(String tiers_paye) {
        this.tiers_paye = tiers_paye;
    }

    public String getFacture_origine() {
        return facture_origine;
    }

    public void setFacture_origine(String facture_origine) {
        this.facture_origine = facture_origine;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getPremiere_echeance() {
        return premiere_echeance;
    }

    public void setPremiere_echeance(Date premiere_echeance) {
        this.premiere_echeance = premiere_echeance;
    }

    public String getCondition_paiement() {
        return condition_paiement;
    }

    public void setCondition_paiement(String condition_paiement) {
        this.condition_paiement = condition_paiement;
    }

    public String getRegime_taxe() {
        return regime_taxe;
    }

    public void setRegime_taxe(String regime_taxe) {
        this.regime_taxe = regime_taxe;
    }

    public String getTotal_ht() {
        return total_ht;
    }

    public void setTotal_ht(String total_ht) {
        this.total_ht = total_ht;
    }

    public String getTotal_taxe() {
        return total_taxe;
    }

    public void setTotal_taxe(String total_taxe) {
        this.total_taxe = total_taxe;
    }

    public String getTotal_ttc() {
        return total_ttc;
    }

    public void setTotal_ttc(String total_ttc) {
        this.total_ttc = total_ttc;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
