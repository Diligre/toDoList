package com.diligre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTastDto {

        private Long id;

        private String name;

        private Boolean status;

        private Long priority;

        private java.sql.Date deadLine;

        private Long projectId;

    }


