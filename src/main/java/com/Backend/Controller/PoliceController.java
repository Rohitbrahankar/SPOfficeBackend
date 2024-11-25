package com.Backend.Controller;

import com.Backend.Dto.PoliceDto;
import com.Backend.Entities.Police;
import com.Backend.Service.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/polices")
public class PoliceController extends BaseController  {

    @Autowired
    private PoliceService policeService;

    // Get all Polices
    @GetMapping
    public ResponseEntity<Set<PoliceDto>> getAllPolices() {
        try {
            Set<PoliceDto> polices = policeService.getAllPolices();
            return ResponseEntity.ok(polices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // // Get polices of subadmin
    // @GetMapping("/subadmin/{subadmin_id}")
    // public ResponseEntity<List<Police>> getPolicesofSubadmin(@PathVariable Long subadmin_id) {
    //     try {
    //         List<Police> polices = policeService.getPolicesofSubadmin(subadmin_id);
    //         return ResponseEntity.ok(polices);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    //     }
    // }

    // Get Police by ID
    @GetMapping("/{id}")
    public ResponseEntity<Police> getPoliceById(@PathVariable Long id) {
        try {
            Optional<Police> police = policeService.getPoliceById(id);
            return police.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Create a new Police
    @PostMapping
    public ResponseEntity<Police> createPolice(@RequestBody PoliceDto policeDto) {
        try {
            Police createdPolice = policeService.createPolice(policeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPolice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update Police
    @PutMapping("/{id}")
    public ResponseEntity<Police> updatePolice(@PathVariable Long id, @RequestBody Police policeDetails) {
        try {
            Police updatedPolice = policeService.updatePolice(id, policeDetails);
            if (updatedPolice != null) {
                return ResponseEntity.ok(updatedPolice);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete Police
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolice(@PathVariable Long id) {
        try {
            boolean deleted = policeService.deletePolice(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
