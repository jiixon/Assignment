package com.garchon.webDBAssignment.dto;

import lombok.Data;

@Data
public class CommentDto {
    String inputLine;
    Long pk;
    Long usertoken;
    String username;
}
