package com.garchon.webDBAssignment.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CheerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTOPK")
    Long id;

    @ManyToOne
    @JoinColumn(name = "PK")
    TableEntity tableEntity;

    @Column(name = "CHEERMEMBER")
    Long usertoken;
}
