package com.Backend.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Entities.Attendance;
import com.Backend.Service.AttendanceService;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController extends BaseController{

    @Autowired
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService){
        this.attendanceService = attendanceService;
    }

    @GetMapping("/patrolling/{id}")
    public ResponseEntity<Attendance> getAttendanceByPatrollingId(@PathVariable Long id){
        Optional<Attendance> attendance = attendanceService.getAttendanceByPatrollingId(id);
        return attendance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
