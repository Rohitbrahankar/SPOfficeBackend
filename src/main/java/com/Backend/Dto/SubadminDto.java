package com.Backend.Dto;

import java.util.Set;

import com.Backend.Entities.Attendance;
import com.Backend.Entities.Subadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubadminDto {

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String station;
    private Long adminId;
    private Set<Attendance> attendances;
    private Set<PoliceDto> polices;
    private String status;

    public SubadminDto buildSubadmin(Subadmin subadmin, Set<PoliceDto> policeDtos) {
        SubadminDto subadminDto = SubadminDto.builder()
                .id(subadmin.getId())
                .username(subadmin.getUsername())
                .phone(subadmin.getPhone())
                .email(subadmin.getEmail())
                .station(subadmin.getStation())
                .adminId(subadmin.getAdmin().getId())
                .status(subadmin.getStatus())
                .polices(policeDtos)
                .build();
        return subadminDto;
    }
    public SubadminDto buildSubadmin(Subadmin subadmin, Set<PoliceDto> policeDtos, Set<Attendance> attendances) {
        SubadminDto subadminDto = SubadminDto.builder()
                .id(subadmin.getId())
                .username(subadmin.getUsername())
                .phone(subadmin.getPhone())
                .email(subadmin.getEmail())
                .station(subadmin.getStation())
                .adminId(subadmin.getAdmin().getId())
                .status(subadmin.getStatus())
                .polices(policeDtos)
                .attendances(attendances)
                .build();
        return subadminDto;
    }

}
