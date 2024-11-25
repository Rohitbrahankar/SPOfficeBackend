package com.Backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Backend.Entities.TestEntity;
import com.Backend.Repository.TestRepository;

@Service
public class TestService {
    
    @Autowired
    TestRepository testRepository;

    public List<TestEntity> getAll(){
        return testRepository.findAll();
    }
}
