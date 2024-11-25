package com.Backend.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.Backend.Dto.AdminDto;
import com.Backend.Dto.PatrollingDto;
import com.Backend.Dto.PoliceDto;
import com.Backend.Dto.SubadminDto;
import com.Backend.Entities.Admin;
import com.Backend.Entities.Subadmin;
import com.Backend.Repository.AdminRepository;
import com.Backend.Utils.PasswordChecker;

@Service
public class AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    private final PasswordChecker passwordChecker;

    public AdminService(AdminRepository adminRepository, PasswordChecker passwordChecker) {
        this.adminRepository = adminRepository;
        this.passwordChecker = new PasswordChecker();
    }

    public Optional<Admin> getAdminByUsername(String username) {
        try {
            return adminRepository.getAdminByUsername(username);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch admin by username", e);
        }
    }

    public AdminDto getAdminById(Long id) {
        try {
            Admin admin = adminRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + id));

            Set<SubadminDto> subadmins = admin.getSubadmins().stream()
                    .map(subadmin -> {
                        Set<PoliceDto> policeDtos = subadmin.getPolices().stream()
                                .map(police -> new PoliceDto().buildPolice(police))
                                .collect(Collectors.toSet());

                        return new SubadminDto().buildSubadmin(subadmin, policeDtos);
                    })
                    .collect(Collectors.toSet());

            Set<PatrollingDto> patrollings = admin.getPatrollings().stream()
                    .map(patrolling -> {
                        Map<String, Set<PoliceDto>> attendance = new HashMap<>();
                        if (patrolling.getAttendance() != null) {
                            patrolling.getAttendance().getPolices().forEach(police -> {
                                Subadmin subadmin = police.getSubadmin();
                                Set<PoliceDto> policeDtos = attendance.getOrDefault(subadmin, new HashSet<>());
                                policeDtos.add(new PoliceDto().buildPolice(police));
                                attendance.put(subadmin.subadminString(), policeDtos);
                            });
                        }
                        return new PatrollingDto().buildPatrolling(patrolling, attendance);
                    })
                    .collect(Collectors.toSet());

            return new AdminDto().buildAdmin(admin, subadmins, patrollings);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch admin by ID", e);
        }
    }

    public Admin createAdmin(Admin admin) {
        try {

            admin.setPassword(passwordChecker.encodePassword(admin.getPassword()));

            return adminRepository.save(admin);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create admin", e);
        }
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        try {
            return adminRepository.findById(id)
                    .map(admin -> {
                        admin.setUsername(updatedAdmin.getUsername());
                        admin.setPassword(updatedAdmin.getPassword());
                        admin.setEmail(updatedAdmin.getEmail());
                        admin.setPhone(updatedAdmin.getPhone());
                        admin.setDistrict(updatedAdmin.getDistrict());
                        return adminRepository.save(admin);
                    })
                    .orElseGet(() -> {
                        updatedAdmin.setId(id);
                        return adminRepository.save(updatedAdmin);
                    });
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update admin", e);
        }
    }

    public void deleteAdmin(Long id) {
        try {
            adminRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete admin", e);
        }
    }
}
