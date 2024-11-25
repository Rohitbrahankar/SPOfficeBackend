package com.Backend.Service;

import com.Backend.Dto.PatrollingDto;
import com.Backend.Dto.PoliceDto;
import com.Backend.Entities.Admin;
import com.Backend.Entities.Attendance;
import com.Backend.Entities.Patrolling;
import com.Backend.Entities.Police;
import com.Backend.Entities.Subadmin;
import com.Backend.Repository.AdminRepository;
import com.Backend.Repository.PatrollingRepository;
import com.Backend.Repository.PoliceRepository;
import com.Backend.Repository.SubadminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatrollingService {

    @Autowired
    private PatrollingRepository patrollingRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PoliceRepository policeRepository;
    @Autowired
    private SubadminRepository subadminRepository;
    @Autowired
    private AttendanceService attendanceService;

    public List<Patrolling> getAllPatrollings() {
        return patrollingRepository.findAll();
    }

    public Set<PatrollingDto> getPatrollings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            username = authentication.getName();
        }
        Optional<Subadmin> subadmin = subadminRepository.getSubdminByUsername(username);
        if(subadmin.isPresent()){
            Subadmin sub = subadmin.get();

            List<Patrolling> patrollings = patrollingRepository.findPatrollingsOfAdmin(sub.getAdmin().getId());
            return patrollings.stream()
                .map(patrolling -> getPatrollingById(patrolling.getId()))
                .collect(Collectors.toSet());
        }
        else{
            Optional<Admin> admin = adminRepository.getAdminByUsername(username);
            Admin ad = admin.get();
            List<Patrolling> patrollings = patrollingRepository.findPatrollingsOfAdmin(ad.getId());
            return patrollings.stream()
                .map(patrolling -> getPatrollingById(patrolling.getId()))
                .collect(Collectors.toSet());
        }
    }

    public Set<PatrollingDto> getPatrollingsOfAdmin(Long adminId) {
        List<Patrolling> patrollings = patrollingRepository.findPatrollingsOfAdmin(adminId);

        if (patrollings == null || patrollings.isEmpty()) {
            return Collections.emptySet();
        }

        return patrollings.stream()
                .map(patrolling -> getPatrollingById(patrolling.getId()))
                .collect(Collectors.toSet());
    }

    public List<Patrolling> getPatrollingsOfSubdmin(Long subadmin_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            username = authentication.getName();
        }
        Optional<Subadmin> subadmin = subadminRepository.getSubdminByUsername(username);
        Subadmin sub = subadmin.get();
        return patrollingRepository.findPatrollingsOfAdmin(sub.getAdmin().getId());
    }

    public PatrollingDto getPatrollingById(Long id) {
        Patrolling patrolling = patrollingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patrolling not found with id: " + id));

        if (patrolling.getAttendance() == null || patrolling.getAttendance().getPolices() == null) {
            return new PatrollingDto().buildPatrolling(patrolling, Collections.emptyMap());
        }

        // Transform the Police entities into PoliceDto and group by Subadmin ID
        Map<String, Set<PoliceDto>> attendance = patrolling.getAttendance().getPolices().stream()
                .collect(Collectors.groupingBy(
                        police -> police.getSubadmin().subadminString(),
                        Collectors.mapping(
                                police -> new PoliceDto().buildPolice(police),
                                Collectors.toSet())));

        return new PatrollingDto().buildPatrolling(patrolling, attendance);
    }

    public Patrolling createPatrolling(PatrollingDto patrollingDTO) {
        Patrolling patrolling = new Patrolling();
        patrolling.setDate(patrollingDTO.getDate());
        patrolling.setEventname(patrollingDTO.getEventname());
        patrolling.setDescription(patrollingDTO.getDescription());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            username = authentication.getName();
        }
        Optional<Admin> optionalAdmin = adminRepository.getAdminByUsername(username);
        Admin admin = optionalAdmin.orElseThrow(() -> new IllegalArgumentException("Admin not found with username"));
        patrolling.setAdmin(admin);

        if (patrollingDTO.getHead() != null) {
            Police head = policeRepository.findById(patrollingDTO.getHead().getId())
                    .orElseThrow(() -> new RuntimeException("Police not found"));
            patrolling.setHead(head);
        }
        if (patrollingDTO.getCohead() != null) {
            Police cohead = policeRepository.findById(patrollingDTO.getCohead().getId())
                    .orElseThrow(() -> new RuntimeException("Police not found"));
            patrolling.setCohead(cohead);
        }

        return patrollingRepository.save(patrolling);
    }

    public Patrolling updatePatrolling(Long id, PatrollingDto patrollingDTO) {
        try {
            Patrolling patrolling = patrollingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Patrolling not found"));

            patrolling.setDate(patrollingDTO.getDate());
            patrolling.setEventname(patrollingDTO.getEventname());
            patrolling.setDescription(patrollingDTO.getDescription());

            if (patrollingDTO.getAdminId() != null) {
                Admin admin = adminRepository.findById(patrollingDTO.getAdminId())
                        .orElseThrow(() -> new RuntimeException("Admin not found"));
                patrolling.setAdmin(admin);
            }

            if (patrollingDTO.getHead() != null) {
                Police head = policeRepository.findById(patrollingDTO.getHead().getId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                patrolling.setHead(head);
            }
            if (patrollingDTO.getCohead() != null) {
                Police cohead = policeRepository.findById(patrollingDTO.getCohead().getId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                patrolling.setCohead(cohead);
            }

            return patrollingRepository.save(patrolling);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update Patrolling", e);
        }
    }

    public boolean deletePatrolling(Long id) {
        try {
            patrollingRepository.deleteById(id);
            return true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete Patrolling", e);
        }
    }

    public Patrolling createAttendance(Long id) {
        try {
            Patrolling patrolling = patrollingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Patrolling not found"));

            Attendance attendance = attendanceService.createAttendance(id);

            patrolling.setAttendance(attendance);

            return patrollingRepository.save(patrolling);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create Attendance", e);
        }
    }

    public Patrolling sendAttendance(Long patrolling_id, Long subadmin_id, List<Long> polices) {
        try {
            System.out.println(patrolling_id +" "+subadmin_id+" "+polices);
            Patrolling patrolling = patrollingRepository.findById(patrolling_id)
                    .orElseThrow(() -> new RuntimeException("Patrolling not found"));
            if(patrolling.getAttendance() == null){
                patrolling = createAttendance(patrolling_id);
            }
            Attendance attendance = attendanceService.sendAttendance(patrolling.getAttendance().getId(), subadmin_id,
                    polices);

            patrolling.setAttendance(attendance);

            return patrollingRepository.save(patrolling);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create Attendance", e);
        }
    }

}
