package com.Backend.Repository;

import com.Backend.Entities.Police;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceRepository extends JpaRepository<Police, Long> {

    // @Query(value = "SELECT * FROM polices  WHERE subadmin_id = :subadmin_id", nativeQuery = true)
    // List<Police> findPolicesofSubadmin(Long subadmin_id);
}
