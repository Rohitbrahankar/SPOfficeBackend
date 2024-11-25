package com.Backend.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.Backend.Entities.Area;

@Getter
@Setter
public class SectorDto {
    private Long id;
    private String sectorName;
    private PoliceDto head;
    private Area area;
    private Set<Long> locationIds;
}
