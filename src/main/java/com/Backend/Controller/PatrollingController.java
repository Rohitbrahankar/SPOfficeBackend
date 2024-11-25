package com.Backend.Controller;

import java.util.List;
import java.util.Set;

import com.Backend.Dto.AttendanceDTO;
import com.Backend.Dto.PatrollingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Entities.Patrolling;
import com.Backend.Service.PatrollingService;

@RestController
@RequestMapping("/api/patrollings")
public class PatrollingController extends BaseController  {

    @Autowired
    private PatrollingService patrollingService;

    // Create a new Patrolling
    @PostMapping
    public ResponseEntity<Patrolling> createPatrolling(@RequestBody PatrollingDto patrollingDTO) {
        try {
            Patrolling patrolling = patrollingService.createPatrolling(patrollingDTO);
            return ResponseEntity.ok(patrolling);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update Patrolling
    @PutMapping("/{id}")
    public ResponseEntity<Patrolling> updatePatrolling(@PathVariable Long id, @RequestBody PatrollingDto patrollingDTO) {
        try {
            Patrolling patrolling = patrollingService.updatePatrolling(id, patrollingDTO);
            return ResponseEntity.ok(patrolling);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete Patrolling
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatrolling(@PathVariable Long id) {
        try {
            boolean deleted = patrollingService.deletePatrolling(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Get Patrolling by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatrollingDto> getPatrollingById(@PathVariable Long id) {
        try {
            PatrollingDto patrolling = patrollingService.getPatrollingById(id);
            return ResponseEntity.ok(patrolling);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get Patrollings of loggedin user
    @GetMapping
    public ResponseEntity<Set<PatrollingDto>> getPatrollings() {
        try {
            Set<PatrollingDto> patrollings = patrollingService.getPatrollings();
            return ResponseEntity.ok(patrollings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get Patrollings of admin
    @GetMapping("/admin/{admin_id}")
    public ResponseEntity<Set<PatrollingDto>> getPatrollingsOfAdmin(@PathVariable Long admin_id) {
        try {
            Set<PatrollingDto> patrollings = patrollingService.getPatrollingsOfAdmin(admin_id);
            return ResponseEntity.ok(patrollings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get Patrollings of subadmin
    @GetMapping("/subadmin/{subadmin_id}")
    public ResponseEntity<List<Patrolling>> getPatrollingsOfSubadmin(@PathVariable Long subadmin_id) {
        try {
            List<Patrolling> patrollings = patrollingService.getPatrollingsOfSubdmin(subadmin_id);
            return ResponseEntity.ok(patrollings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/create-attendance/{patrolling_id}")
    public ResponseEntity<Patrolling> createAttendance(@PathVariable Long patrolling_id) {
        try {
            Patrolling patrolling = patrollingService.createAttendance(patrolling_id);
            return ResponseEntity.ok(patrolling);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // send attendance for subadmin
    @PutMapping("/send-attendance")
    public ResponseEntity<Patrolling> sendAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            Patrolling patrolling = patrollingService.sendAttendance(attendanceDTO.getPatrollingId(), attendanceDTO.getSubadminId(), attendanceDTO.getPolices());
            return ResponseEntity.ok(patrolling);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
