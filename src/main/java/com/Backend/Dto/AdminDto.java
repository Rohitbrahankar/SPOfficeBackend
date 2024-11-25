package com.Backend.Dto;

import java.util.Set;

import com.Backend.Entities.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String district;
    private Set<SubadminDto> subadmins;
    private Set<PatrollingDto> patrollings;

    public AdminDto buildAdmin(Admin admin, Set<SubadminDto> subadmins, Set<PatrollingDto> patrollings) {
        AdminDto adminDto = AdminDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .district(admin.getDistrict())
                .subadmins(subadmins)
                .patrollings(patrollings)
                .build();
        return adminDto;
    }

}
