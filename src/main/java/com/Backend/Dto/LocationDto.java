package com.Backend.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

import com.Backend.Entities.Police;

@Getter
@Setter
public class LocationDto {
    private Long id;
    private String locationName;
    private Long headId;
    private Long sectorId;
    private List<Police> polices;
    private List<String> equipments;
    private Set<Long> policeIds;
}
