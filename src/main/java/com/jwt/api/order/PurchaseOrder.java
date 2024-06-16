package com.jwt.api.order;

import com.jwt.api.invoice.Invoice;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;


    @OneToOne(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private Invoice invoice;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
