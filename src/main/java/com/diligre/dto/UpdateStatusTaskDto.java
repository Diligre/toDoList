package com.diligre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateStatusTaskDto {
    
    private Long id;
    
    private Boolean status;
    
}
