package com.garchon.webDBAssignment.entity;

import com.garchon.webDBAssignment.entity.type.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class DetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PKID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name ="PK")
    private TableEntity tableEntity;

    @Column(name = "MAINCONTENTS")
    private String mainContents;

    @Column(name = "SCHEDULECONTENTS") //일정 상세 내역
    private String scheduleContents;

    @Column(name = "SCHEDULETIME") //일정 상세 시간
    private String scheduleTime;

    @Column(name = "SCHEDULERESULT") //일정 완료 여부
    private String scheduleResult;
}
