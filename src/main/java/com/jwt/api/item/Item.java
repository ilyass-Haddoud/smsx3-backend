package com.jwt.api.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwt.api.invoice.Invoice;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonIgnoreProperties("items")
    private Invoice invoice;

    @Column(name = "origineLigne")
    private String origineLigne;

    @Column(name = "numeroOrigine", nullable = false)
    private String numeroOrigine;

    @Column(name = "ligneOrigine")
    private String ligneOrigine;

    @Column(name = "sequenceOrigine")
    private String sequenceOrigine;

    @Column(name = "article", nullable = false)
    private String article;

    @Column(name = "designation")
    private String designation;

    @Column(name = "uniteFacturation", nullable = false)
    private String uniteFacturation = "UN";

    @Column(name = "quantiteFacturee", nullable = false)
    private Integer quantiteFacturee;

    @Column(name = "prixNet")
    private BigDecimal prixNet;

    @Column(name = "montantLigneHT", nullable = false)
    private BigDecimal montantLigneHT;

    @Column(name = "valeurRemiseFrais1")
    private Integer valeurRemiseFrais1 = 0;

    @Column(name = "valeurRemiseFrais2")
    private Integer valeurRemiseFrais2 = 0;

    @Column(name = "valeurRemiseFrais3")
    private Integer valeurRemiseFrais3 = 0;

    @Column(name = "affaire")
    private String affaire;

    @Column(name = "texteLigne91")
    private String texteLigne91;

    @Column(name = "texteLigne92")
    private String texteLigne92;

    // Getters and setters for each field


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getOrigineLigne() {
        return origineLigne;
    }

    public void setOrigineLigne(String origineLigne) {
        this.origineLigne = origineLigne;
    }

    public String getNumeroOrigine() {
        return numeroOrigine;
    }

    public void setNumeroOrigine(String numeroOrigine) {
        this.numeroOrigine = numeroOrigine;
    }

    public String getLigneOrigine() {
        return ligneOrigine;
    }

    public void setLigneOrigine(String ligneOrigine) {
        this.ligneOrigine = ligneOrigine;
    }

    public String getSequenceOrigine() {
        return sequenceOrigine;
    }

    public void setSequenceOrigine(String sequenceOrigine) {
        this.sequenceOrigine = sequenceOrigine;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUniteFacturation() {
        return uniteFacturation;
    }

    public void setUniteFacturation(String uniteFacturation) {
        this.uniteFacturation = uniteFacturation;
    }

    public Integer getQuantiteFacturee() {
        return quantiteFacturee;
    }

    public void setQuantiteFacturee(Integer quantiteFacturee) {
        this.quantiteFacturee = quantiteFacturee;
    }

    public BigDecimal getPrixNet() {
        return prixNet;
    }

    public void setPrixNet(BigDecimal prixNet) {
        this.prixNet = prixNet;
    }

    public BigDecimal getMontantLigneHT() {
        return montantLigneHT;
    }

    public void setMontantLigneHT(BigDecimal montantLigneHT) {
        this.montantLigneHT = montantLigneHT;
    }

    public Integer getValeurRemiseFrais1() {
        return valeurRemiseFrais1;
    }

    public void setValeurRemiseFrais1(Integer valeurRemiseFrais1) {
        this.valeurRemiseFrais1 = valeurRemiseFrais1;
    }

    public Integer getValeurRemiseFrais2() {
        return valeurRemiseFrais2;
    }

    public void setValeurRemiseFrais2(Integer valeurRemiseFrais2) {
        this.valeurRemiseFrais2 = valeurRemiseFrais2;
    }

    public Integer getValeurRemiseFrais3() {
        return valeurRemiseFrais3;
    }

    public void setValeurRemiseFrais3(Integer valeurRemiseFrais3) {
        this.valeurRemiseFrais3 = valeurRemiseFrais3;
    }

    public String getAffaire() {
        return affaire;
    }

    public void setAffaire(String affaire) {
        this.affaire = affaire;
    }

    public String getTexteLigne91() {
        return texteLigne91;
    }

    public void setTexteLigne91(String texteLigne91) {
        this.texteLigne91 = texteLigne91;
    }

    public String getTexteLigne92() {
        return texteLigne92;
    }

    public void setTexteLigne92(String texteLigne92) {
        this.texteLigne92 = texteLigne92;
    }
}
