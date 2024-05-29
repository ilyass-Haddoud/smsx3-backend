package com.jwt.api.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwt.api.item.Item;
import com.jwt.api.supplier.Supplier;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties("invoices")
    private Supplier supplier;

    @Column(name = "site", nullable = false)
    private String site;

    @Column(name = "typeFacture", nullable = false)
    private String typeFacture = "INV";

    @Column(name = "numeroPiece")
    private String numeroPiece;

    @Column(name = "dateComptable", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateComptable;

    @Column(name = "tiers", nullable = false)
    private String tiers;

    @Column(name = "collectif", nullable = false)
    private String collectif = "PL";

    @Column(name = "devise", nullable = false)
    private String devise;

    @Column(name = "bonAPayer", nullable = false)
    private Integer bonAPayer = 1;

    @Column(name = "documentOrigine", nullable = false)
    private String documentOrigine;

    @Column(name = "dateOrigine", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOrigine;

    @Column(name = "referenceInterne")
    private String referenceInterne;

    @Column(name = "commentaires0")
    private String commentaires0;

    @Column(name = "commentaires1")
    private String commentaires1;

    @Column(name = "commentaires2")
    private String commentaires2;

    @Column(name = "totalHTLignes", nullable = false)
    private BigDecimal totalHTLignes;

    @Column(name = "totalTaxes")
    private BigDecimal totalTaxes;

    @Column(name = "montantTTC")
    private BigDecimal montantTTC;

    @Column(name = "etat")
    private Integer etat = 1;

    @Column(name = "texteEntete71")
    private String texteEntete71;

    @Column(name = "texteEntete72")
    private String texteEntete72;

    @Column(name = "textePied81")
    private String textePied81;

    @Column(name = "textePied82")
    private String textePied82;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("invoice")
    private List<Item> items;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "document")
    private String document;

    // Getters and setters for each field

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(String typeFacture) {
        this.typeFacture = typeFacture;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public Date getDateComptable() {
        return dateComptable;
    }

    public void setDateComptable(Date dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getTiers() {
        return tiers;
    }

    public void setTiers(String tiers) {
        this.tiers = tiers;
    }

    public String getCollectif() {
        return collectif;
    }

    public void setCollectif(String collectif) {
        this.collectif = collectif;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Integer getBonAPayer() {
        return bonAPayer;
    }

    public void setBonAPayer(Integer bonAPayer) {
        this.bonAPayer = bonAPayer;
    }

    public String getDocumentOrigine() {
        return documentOrigine;
    }

    public void setDocumentOrigine(String documentOrigine) {
        this.documentOrigine = documentOrigine;
    }

    public Date getDateOrigine() {
        return dateOrigine;
    }

    public void setDateOrigine(Date dateOrigine) {
        this.dateOrigine = dateOrigine;
    }

    public String getReferenceInterne() {
        return referenceInterne;
    }

    public void setReferenceInterne(String referenceInterne) {
        this.referenceInterne = referenceInterne;
    }

    public String getCommentaires0() {
        return commentaires0;
    }

    public void setCommentaires0(String commentaires0) {
        this.commentaires0 = commentaires0;
    }

    public String getCommentaires1() {
        return commentaires1;
    }

    public void setCommentaires1(String commentaires1) {
        this.commentaires1 = commentaires1;
    }

    public String getCommentaires2() {
        return commentaires2;
    }

    public void setCommentaires2(String commentaires2) {
        this.commentaires2 = commentaires2;
    }

    public BigDecimal getTotalHTLignes() {
        return totalHTLignes;
    }

    public void setTotalHTLignes(BigDecimal totalHTLignes) {
        this.totalHTLignes = totalHTLignes;
    }

    public BigDecimal getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(BigDecimal totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public BigDecimal getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(BigDecimal montantTTC) {
        this.montantTTC = montantTTC;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getTexteEntete71() {
        return texteEntete71;
    }

    public void setTexteEntete71(String texteEntete71) {
        this.texteEntete71 = texteEntete71;
    }

    public String getTexteEntete72() {
        return texteEntete72;
    }

    public void setTexteEntete72(String texteEntete72) {
        this.texteEntete72 = texteEntete72;
    }

    public String getTextePied81() {
        return textePied81;
    }

    public void setTextePied81(String textePied81) {
        this.textePied81 = textePied81;
    }

    public String getTextePied82() {
        return textePied82;
    }

    public void setTextePied82(String textePied82) {
        this.textePied82 = textePied82;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
