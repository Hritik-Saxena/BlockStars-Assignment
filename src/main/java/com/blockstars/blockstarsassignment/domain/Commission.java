package com.blockstars.blockstarsassignment.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "commissions")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commission_id")
    private Long commissionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Reference to the User entity

    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;

    @Column(name = "commission_date")
    private LocalDateTime commissionDate;

    @Column(name = "commission_type")
    private String commissionType;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "transaction_reference")
    private String transactionReference;

    // Constructors, getters, and setters

    public Long getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(Long commissionId) {
        this.commissionId = commissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public LocalDateTime getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(LocalDateTime commissionDate) {
        this.commissionDate = commissionDate;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }
}
