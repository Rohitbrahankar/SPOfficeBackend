package com.Backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Backend.Entities.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT * FROM attendances WHERE patrolling_id = :patrolling_id", nativeQuery = true)
    Optional<Attendance> findAttendanceByPatrollingId(Long patrolling_id);

}
