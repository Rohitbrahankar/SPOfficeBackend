package com.Backend.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaDto {
    private Long id;
    private String areaName;
    private Long headId;
    private Long coheadId;
    private Long subPatrollingId;
}
