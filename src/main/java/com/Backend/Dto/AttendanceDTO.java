package com.Backend.Dto;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceDTO {

    private Long id;
    private Long subadminId;
    private Long patrollingId;
    private List<Long> polices;
}
