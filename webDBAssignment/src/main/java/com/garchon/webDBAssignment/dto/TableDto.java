package com.garchon.webDBAssignment.dto;

import com.garchon.webDBAssignment.entity.type.Type;
import lombok.Data;


@Data
public class TableDto {
    Type type;
    String title;
    Boolean deleteYn;
    Long cheerCnt;
    String writer;
    String scheduleContents;
    String scheduleTime;
    Type scheduleResult;
    String mainContents;
}
