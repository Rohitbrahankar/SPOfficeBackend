package com.Backend.Service;

import com.Backend.Dto.AreaDto;
import com.Backend.Entities.Area;
import com.Backend.Entities.Police;
import com.Backend.Entities.SubPatrolling;
import com.Backend.Repository.AreaRepository;
import com.Backend.Repository.PoliceRepository;
import com.Backend.Repository.SubPatrollingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private PoliceRepository policeRepository;
    @Autowired
    private SubPatrollingRepository subPatrollingRepository;

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public Optional<Area> getAreaById(Long id) {
        return areaRepository.findById(id);
    }

    public Area createArea(AreaDto areaDTO) {
        try {

            Area area = new Area();
            area.setAreaName(areaDTO.getAreaName());

            if (areaDTO.getHeadId() != null) {
                Police head = policeRepository.findById(areaDTO.getHeadId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                area.setHead(head);
            }
            if (areaDTO.getCoheadId() != null) {
                Police cohead = policeRepository.findById(areaDTO.getCoheadId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                area.setCohead(cohead);
            }

            if (areaDTO.getSubPatrollingId() != null) {
                SubPatrolling subPatrolling = subPatrollingRepository.findById(areaDTO.getSubPatrollingId())
                        .orElseThrow(() -> new RuntimeException("SubPatrolling not found"));
                area.setSubPatrolling(subPatrolling);
            }
            return areaRepository.save(area);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create area", e);
        }

    }

    public Area updateArea(Long id, AreaDto areaDTO) {
        try {
            Area existingArea = areaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Area not found"));
            existingArea.setAreaName(areaDTO.getAreaName());

            if (areaDTO.getHeadId() != null) {
                Police head = policeRepository.findById(areaDTO.getHeadId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                existingArea.setHead(head);
            }

            if (areaDTO.getSubPatrollingId() != null) {
                SubPatrolling subPatrolling = subPatrollingRepository.findById(areaDTO.getSubPatrollingId())
                        .orElseThrow(() -> new RuntimeException("SubPatrolling not found"));
                existingArea.setSubPatrolling(subPatrolling);
            }
            return areaRepository.save(existingArea);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update area", e);
        }
    }

    public boolean deleteArea(Long id) {
        try {
            areaRepository.deleteById(id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
