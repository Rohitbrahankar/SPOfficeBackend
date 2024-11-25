package com.Backend.Dto;

import com.Backend.Entities.Police;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoliceDto {
    private Long id;
    private String fullname;
    private Long policeId;
    private String phone;
    private String email;
    private String gender;
    private String designation;
    private SubadminDto subadmin;
    // private Long areaId;
    // private Long sectorId;

    public PoliceDto buildPolice(Police police){
        PoliceDto policeDto = PoliceDto.builder()
        .id(police.getId())
        .fullname(police.getFullname())
        .policeId(police.getPoliceId())
        .phone(police.getPhone())
        .email(police.getEmail())
        .gender(police.getGender())
        .designation(police.getDesignation())
        .subadmin(new SubadminDto().buildSubadmin(police.getSubadmin(), null))
        .build();
        return policeDto;
    }

}
