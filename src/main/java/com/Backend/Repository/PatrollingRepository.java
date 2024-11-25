package com.Backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Backend.Entities.Patrolling;

public interface PatrollingRepository extends JpaRepository<Patrolling, Long> {
    @Query(value = "SELECT * FROM patrollings  WHERE admin_id = :admin_id", nativeQuery = true)
    List<Patrolling> findPatrollingsOfAdmin(Long admin_id);
}
