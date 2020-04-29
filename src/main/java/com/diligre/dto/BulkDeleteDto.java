package com.diligre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BulkDeleteDto {

    private List<Long> ids;

}
