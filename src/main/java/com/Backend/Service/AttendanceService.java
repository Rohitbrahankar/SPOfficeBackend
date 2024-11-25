package com.Backend.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.Backend.Entities.Attendance;
import com.Backend.Entities.Patrolling;
import com.Backend.Entities.Police;
import com.Backend.Entities.Subadmin;
import com.Backend.Repository.AttendanceRepository;
import com.Backend.Repository.PatrollingRepository;
import com.Backend.Repository.PoliceRepository;
import com.Backend.Repository.SubadminRepository;

@Service
public class AttendanceService {

    @Autowired
    private final AttendanceRepository attendanceRepository;
    @Autowired
    private final PatrollingRepository patrollingRepository;
    @Autowired
    private final PoliceRepository policeRepository;
    @Autowired
    private final SubadminRepository subadminRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, PatrollingRepository patrollingRepository,
            PoliceRepository policeRepository, SubadminRepository subadminRepository) {
        this.attendanceRepository = attendanceRepository;
        this.patrollingRepository = patrollingRepository;
        this.policeRepository = policeRepository;
        this.subadminRepository = subadminRepository;
    }

    public Attendance createAttendance(Long patrolling_id) {
        Attendance attendance = new Attendance();

        if (patrolling_id != null) {
            Patrolling patrolling = patrollingRepository.findById(patrolling_id)
                    .orElseThrow(() -> new RuntimeException("Patrolling not found"));
            attendance.setPatrolling(patrolling);
        }

        return attendanceRepository.save(attendance);
    }

    public Attendance sendAttendance(Long attendance_id, Long subadmin_id, List<Long> polices) {

        try {

            if (attendance_id == null || polices == null)
                throw new IllegalArgumentException("Attendance ID or Polices cannot be null");

            Attendance attendance = attendanceRepository.findById(attendance_id)
                    .orElseThrow(() -> new RuntimeException("Attendance not found"));
            Subadmin subadmin = subadminRepository.findById(subadmin_id)
                    .orElseThrow(() -> new RuntimeException("Subadmin not found"));

            Set<Police> polices2 = attendance.getPolices();
            for (Long id : polices) {
                Police police = policeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                polices2.add(police);
            }

            attendance.setPolices(polices2);
            Set<Subadmin> subadmins = attendance.getSubadmins();
            subadmins.add(subadmin);
            attendance.setSubadmins(subadmins);
            return attendanceRepository.save(attendance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send attendance", e);
        }
    }

    public Optional<Attendance> getAttendanceByPatrollingId(Long patrolling_id) {
        try {
            return attendanceRepository.findAttendanceByPatrollingId(patrolling_id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch attendance by patrolliing id", e);
        }
    }

}
