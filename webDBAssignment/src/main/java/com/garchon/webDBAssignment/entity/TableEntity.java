package com.garchon.webDBAssignment.entity;

import com.garchon.webDBAssignment.entity.type.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK")
    private Long Pk;

    @Column(name = "TYPE") //Befor,After
    private String type;

    @Column(name = "TITLE") //제목
    private String title;

    @Column(name = "UPDATEDATE") //글 쓴 시간
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "DELETEYN") //삭제 여부
    private Boolean deleteYn;

    @Column(name = "CHEERCNT") //좋아요 수
    private Long cheerCnt;

    @Column(name = "WRITER") //작성자
    private String writer;

    @Column(name = "USERTOKEN")
    private Long userToken;

}