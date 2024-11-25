package com.Backend.Dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubPatrollingDto {
    private Long id;
    private PoliceDto head;
    private PoliceDto cohead;
    private String description;
    private String instructions;
    private Long patrollingId;
    private String subpatrollingname;
}
