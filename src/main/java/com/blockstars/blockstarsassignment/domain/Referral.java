package com.blockstars.blockstarsassignment.domain;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_referrals")
public class Referral {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name= "level")
    private int level;

    @ManyToOne
    @JoinColumn(name = "referrer_id")
    @JsonIgnore
    private User referrer;

    @ManyToOne
    @JoinColumn(name = "referred_id")
    private User referred;

    @Column(name = "referral_date")
    private LocalDateTime referralDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_ts", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTs;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_ts", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedTs;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReferrer() {
        return referrer;
    }

    public void setReferrer(User referrer) {
        this.referrer = referrer;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public User getReferred() {
        return referred;
    }

    public void setReferred(User referred) {
        this.referred = referred;
    }

    public LocalDateTime getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(LocalDateTime referralDate) {
        this.referralDate = referralDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }
}
