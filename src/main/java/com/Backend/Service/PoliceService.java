package com.Backend.Service;

import com.Backend.Dto.PoliceDto;
import com.Backend.Dto.SubadminDto;
import com.Backend.Entities.*;
import com.Backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PoliceService {

    @Autowired
    private PoliceRepository policeRepository;

    @Autowired
    private SubadminRepository subadminRepository;

    @Autowired
    private SubadminService subadminService;

    // @Autowired
    // private RequestRepository requestRepository;

    // @Autowired
    // private AreaRepository areaRepository;

    // @Autowired
    // private SectorRepository sectorRepository;

    // Get all Police
    public Set<PoliceDto> getAllPolices() {
        try {
            List<Police> polices = policeRepository.findAll();
            Set<PoliceDto> policesDto = polices.stream().map(police -> new PoliceDto().buildPolice(police))
                                .collect(Collectors.toSet());
            return policesDto;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all polices", e);
        }
    }

    // // Get Polices for subadmin
    // public List<Police> getPolicesofSubadmin(Long subadmin_id) {
    //     try {
    //         return policeRepository.findPolicesofSubadmin(subadmin_id);
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error fetching all polices", e);
    //     }
    // }

    // Get Police by ID
    public Optional<Police> getPoliceById(Long id) {
        try {
            return policeRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching police by ID", e);
        }
    }

    // Create a new Police
    public Police createPolice(PoliceDto policeDto) {
        try {
                Police police = new Police();
                police.setFullname(policeDto.getFullname());
                police.setPoliceId(policeDto.getPoliceId());
                police.setPhone(policeDto.getPhone());
                police.setEmail(policeDto.getEmail());
                police.setGender(policeDto.getGender());
                police.setDesignation(policeDto.getDesignation());
                SubadminDto subadmindto = subadminService.currentSubadmin();
                Optional<Subadmin> subadmin = subadminRepository.findById(subadmindto.getId());
                if (subadmin != null) {
                    police.setSubadmin(subadmin.get());
                }
                // if (policeDto.getAreaId() != null) {
                //     Area area = areaRepository.findById(policeDto.getAreaId())
                //             .orElseThrow(() -> new RuntimeException("Area not found"));
                //     police.setArea(area);
                // }
                // if (policeDto.getSectorId() != null) {
                //     Sector sector = sectorRepository.findById(policeDto.getSectorId())
                //             .orElseThrow(() -> new RuntimeException("Sector not found"));
                //     police.setSector(sector);
                // }
                return policeRepository.save(police);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error creating police", e);
        }
    }

    // Update Police
    public Police updatePolice(Long id, Police policeDetails) {
        try {
            Optional<Police> police = policeRepository.findById(id);
            if (police.isPresent()) {
                Police existingPolice = police.get();
                existingPolice.setFullname(policeDetails.getFullname());
                existingPolice.setPoliceId(policeDetails.getPoliceId());
                existingPolice.setPhone(policeDetails.getPhone());
                existingPolice.setEmail(policeDetails.getEmail());
                existingPolice.setDesignation(policeDetails.getDesignation());
                existingPolice.setSubadmin(policeDetails.getSubadmin());
                return policeRepository.save(existingPolice);
            }
            throw new RuntimeException("Police not found");
        } catch (Exception e) {
            throw new RuntimeException("Error updating police", e);
        }
    }

    // Delete Police
    public boolean deletePolice(Long id) {
        try {
            Optional<Police> police = policeRepository.findById(id);
            if (police.isPresent()) {
                policeRepository.delete(police.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting police", e);
        }
    }
}
