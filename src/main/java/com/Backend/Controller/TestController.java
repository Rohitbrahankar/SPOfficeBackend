package com.Backend.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Entities.TestEntity;
import com.Backend.Service.TestService;

@RestController
public class TestController{
    
    @Autowired
    TestService testService;

    @GetMapping("/hello")
    public String Test(){
        return "Hello World";
    }

    @GetMapping("/tests")
    public ResponseEntity<List<TestEntity>> getAll(){
        try {
            return new ResponseEntity<List<TestEntity>>(testService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

}

