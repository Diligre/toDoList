package com.diligre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTastDto {

        private Long id;

        private String name;

        private java.sql.Date deadLine;

        private Long projectId;

    }


