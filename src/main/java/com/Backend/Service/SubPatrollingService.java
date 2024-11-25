package com.Backend.Service;

import com.Backend.Dto.SubPatrollingDto;
import com.Backend.Entities.Patrolling;
import com.Backend.Entities.Police;
import com.Backend.Entities.SubPatrolling;
import com.Backend.Repository.PatrollingRepository;
import com.Backend.Repository.PoliceRepository;
import com.Backend.Repository.SubPatrollingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubPatrollingService {

    @Autowired
    private SubPatrollingRepository subPatrollingRepository;
    @Autowired
    private PoliceRepository policeRepository;
    @Autowired
    private PatrollingRepository patrollingRepository;

    // Get SubPatrolling by ID
    public Optional<SubPatrolling> getSubPatrollingById(Long id) {
        return subPatrollingRepository.findById(id);
    }

    // Create a new SubPatrolling
    public SubPatrolling createSubPatrolling(SubPatrollingDto subPatrollingDTO) {
        try {
            SubPatrolling subPatrolling = new SubPatrolling();

            Police head = policeRepository.findById(subPatrollingDTO.getHead().getId())
                    .orElseThrow(() -> new RuntimeException("Head not found"));
            Police cohead = policeRepository.findById(subPatrollingDTO.getCohead().getId())
                    .orElseThrow(() -> new RuntimeException("Co-head not found"));
            Patrolling patrolling = patrollingRepository.findById(subPatrollingDTO.getPatrollingId())
                    .orElseThrow(() -> new RuntimeException("Patrolling not found"));

            subPatrolling.setHead(head);
            subPatrolling.setCohead(cohead);
            subPatrolling.setDescription(subPatrollingDTO.getDescription());
            subPatrolling.setInstructions(subPatrollingDTO.getInstructions());
            subPatrolling.setPatrolling(patrolling);
            subPatrolling.setSubpatrollingname(subPatrollingDTO.getSubpatrollingname());
            return subPatrollingRepository.save(subPatrolling);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create SubPatrolling", e);
        }
    }

    // Update SubPatrolling
    public SubPatrolling updateSubPatrolling(Long id, SubPatrollingDto updatedSubPatrolling) {
        try {
            return subPatrollingRepository.findById(id)
                    .map(subPatrolling -> {
                        // Update fields
                        Police head = policeRepository.findById(updatedSubPatrolling.getHead().getId())
                                .orElseThrow(() -> new RuntimeException("Head not found"));
                        Police cohead = policeRepository.findById(updatedSubPatrolling.getCohead().getId())
                                .orElseThrow(() -> new RuntimeException("Co-head not found"));
                        Patrolling patrolling = patrollingRepository.findById(updatedSubPatrolling.getPatrollingId())
                                .orElseThrow(() -> new RuntimeException("Patrolling not found"));
                        subPatrolling.setHead(head);
                        subPatrolling.setCohead(cohead);
                        subPatrolling.setDescription(updatedSubPatrolling.getDescription());
                        subPatrolling.setInstructions(updatedSubPatrolling.getInstructions());
                        subPatrolling.setPatrolling(patrolling);
                        return subPatrollingRepository.save(subPatrolling);
                    })
                    .orElseThrow(() -> new RuntimeException("Unable to update Subpatrolling"));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update SubPatrolling", e);
        }
    }

    // Delete SubPatrolling
    public boolean deleteSubPatrolling(Long id) {
        try {
            if (subPatrollingRepository.existsById(id)) {
                subPatrollingRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete SubPatrolling", e);
        }
    }
}
