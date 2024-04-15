package com.jwt.api.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwt.api.claim.Claim;
import com.jwt.api.invoice.Invoice;
import com.jwt.api.role.Role;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "supplier_table")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bpsnum", unique = true,nullable = false)
    private String bpsnum;

    @Column(name = "bpsnam")
    private String bpsnam;

    @Column(name = "bpainv")
    private String bpainv;

    @Column(name = "bpaadd")
    private String bpaadd;

    @Column(name = "bpsrem")
    private String bpsrem;

    @Column(name = "bpsgru")
    private String bpsgru;

    @Column(name = "bpsrsk")
    private String bpsrsk;

    @Column(name = "bsgcod")
    private String bsgcod;

    @Column(name = "bptnum")
    private String bptnum;

    @Column(name = "bpsnumbps")
    private String bpsnumbps;

    @Column(name = "bpsnumtel")
    private String bpsnumtel;

    @Column(name = "bpsaddeml",unique = true,nullable = false)
    private String bpsaddeml;

    @Column(name = "bpspasse",nullable = false)
    private String bpspasse;

    @Column(name = "bpstyp")
    private String bpstyp;

    @Column(name = "bpsfname")
    private String bpsfname;

    @Column(name = "bpslname")
    private String bpslname;


    private boolean mfaEnabled = true;
    private String secret;

    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    @ManyToMany
    @JoinTable(name = "supplier_role_table",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties("suppliers")
    private List<Role> roles;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnoreProperties("supplier")
    private List<Claim> claims;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnoreProperties("supplier")
    private List<Invoice> invoices;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBpsnum() {
        return bpsnum;
    }

    public void setBpsnum(String bpsnum) {
        this.bpsnum = bpsnum;
    }

    public String getBpsnam() {
        return bpsnam;
    }

    public void setBpsnam(String bpsnam) {
        this.bpsnam = bpsnam;
    }

    public String getBpainv() {
        return bpainv;
    }

    public void setBpainv(String bpainv) {
        this.bpainv = bpainv;
    }

    public String getBpaadd() {
        return bpaadd;
    }

    public void setBpaadd(String bpaadd) {
        this.bpaadd = bpaadd;
    }

    public String getBpsrem() {
        return bpsrem;
    }

    public void setBpsrem(String bpsrem) {
        this.bpsrem = bpsrem;
    }

    public String getBpsgru() {
        return bpsgru;
    }

    public void setBpsgru(String bpsgru) {
        this.bpsgru = bpsgru;
    }

    public String getBpsrsk() {
        return bpsrsk;
    }

    public void setBpsrsk(String bpsrsk) {
        this.bpsrsk = bpsrsk;
    }

    public String getBsgcod() {
        return bsgcod;
    }

    public void setBsgcod(String bsgcod) {
        this.bsgcod = bsgcod;
    }

    public String getBptnum() {
        return bptnum;
    }

    public void setBptnum(String bptnum) {
        this.bptnum = bptnum;
    }

    public String getBpsnumbps() {
        return bpsnumbps;
    }

    public void setBpsnumbps(String bpsnumbps) {
        this.bpsnumbps = bpsnumbps;
    }

    public String getBpsnumtel() {
        return bpsnumtel;
    }

    public void setBpsnumtel(String bpsnumtel) {
        this.bpsnumtel = bpsnumtel;
    }

    public String getBpsaddeml() {
        return bpsaddeml;
    }

    public void setBpsaddeml(String bpsaddeml) {
        this.bpsaddeml = bpsaddeml;
    }

    public String getBpspasse() {
        return bpspasse;
    }

    public void setBpspasse(String bpspasse) {
        this.bpspasse = bpspasse;
    }

    public String getBpstyp() {
        return bpstyp;
    }

    public void setBpstyp(String bpstyp) {
        this.bpstyp = bpstyp;
    }

    public String getBpsfname() {
        return bpsfname;
    }

    public void setBpsfname(String bpsfname) {
        this.bpsfname = bpsfname;
    }

    public String getBpslname() {
        return bpslname;
    }

    public void setBpslname(String bpslname) {
        this.bpslname = bpslname;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", bpsnum='" + bpsnum + '\'' +
                ", bpsnam='" + bpsnam + '\'' +
                ", bpainv='" + bpainv + '\'' +
                ", bpaadd='" + bpaadd + '\'' +
                ", bpsrem='" + bpsrem + '\'' +
                ", bpsgru='" + bpsgru + '\'' +
                ", bpsrsk='" + bpsrsk + '\'' +
                ", bsgcod='" + bsgcod + '\'' +
                ", bptnum='" + bptnum + '\'' +
                ", bpsnumbps='" + bpsnumbps + '\'' +
                ", bpsnumtel='" + bpsnumtel + '\'' +
                ", bpsaddeml='" + bpsaddeml + '\'' +
                ", bpspasse='" + bpspasse + '\'' +
                ", bpstyp='" + bpstyp + '\'' +
                ", bpsfname='" + bpsfname + '\'' +
                ", bpslname='" + bpslname + '\'' +
                ", mfaEnabled=" + mfaEnabled +
                ", secret='" + secret + '\'' +
                ", roles=" + roles +
                '}';
    }

}
