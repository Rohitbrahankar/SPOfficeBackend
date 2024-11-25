package com.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.Entities.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    
}
