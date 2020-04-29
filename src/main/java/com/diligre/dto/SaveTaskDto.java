package com.diligre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class SaveTaskDto {

    private Long id;

    private String name;

    private Boolean status;

    private Date deadLine;

    private Long projectId;

}
