package com.currencyExchange.converter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Data
@Table(name="Audit-Table")
public class Audit_Info {
    @Id
    @Column(name= "Request-Id")
    private Integer request_Id;

    @Column(name = "Request-Status")
    private String RequestStatus;

    @Column(name="Request")
    private String request;

    @Column(name="Response")
    private String response;

    @CreationTimestamp
    @Column(name="Created-TS")
    private Date createdTimestamp;

    @UpdateTimestamp
    @Column(name="Updated-TS",updatable = true)
    private Date updatedTimestamp;

}
