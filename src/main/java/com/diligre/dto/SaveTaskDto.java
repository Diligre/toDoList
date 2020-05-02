package com.diligre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class SaveTaskDto {

    private String name;

    private Boolean status = false;

    private Long projectId;

}
