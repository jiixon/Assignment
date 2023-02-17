package com.garchon.webDBAssignment.dto;

import lombok.Data;

@Data
public class DetailFormDto {
    String scheduleName;//글 제목
    String mainContents;//글 내용
    String scheduleContents;//스케줄 내용
    String schedule;//스케줄 내용
    String time;//스케줄 시간
    String result;//스케줄별 완료 혹은 미완료 상태
    String flexRadioDefault;//Before,After
    String userName;
    Long ispk;
    String type;
    Long userToken;

}
