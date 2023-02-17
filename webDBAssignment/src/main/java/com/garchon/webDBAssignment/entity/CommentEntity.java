package com.garchon.webDBAssignment.entity;

import com.garchon.webDBAssignment.entity.DetailEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTOPK")
    Long id;

    @ManyToOne
    @JoinColumn(name = "IDPK")
    DetailEntity detailEntity;

    @Column(name = "COMMENTWRITER")
    String name;

    @Column(name = "USERTOKEN")
    Long userToken;

    @Column(name = "COMMENT")
    String comment;

    @Column(name = "UPDATETIME")
    LocalDateTime localDateTime;
}
