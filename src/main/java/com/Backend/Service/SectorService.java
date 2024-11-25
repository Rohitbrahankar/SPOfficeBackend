package com.Backend.Service;

import com.Backend.Dto.SectorDto;
import com.Backend.Entities.Area;
import com.Backend.Entities.Police;
import com.Backend.Entities.Sector;
import com.Backend.Repository.AreaRepository;
import com.Backend.Repository.PoliceRepository;
import com.Backend.Repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private PoliceRepository policeRepository;
    @Autowired
    private AreaRepository areaRepository;

    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    public Optional<Sector> getSectorById(Long id) {
        return sectorRepository.findById(id);
    }

    public Sector createSector(SectorDto sectorDTO) {

        Sector sector = new Sector();
        sector.setSectorName(sectorDTO.getSectorName());

        if (sectorDTO.getHead().getId() != null) {
            Police head = policeRepository.findById(sectorDTO.getHead().getId())
                    .orElseThrow(() -> new RuntimeException("Police not found"));
            sector.setHead(head);
        }

        if (sectorDTO.getArea().getId() != null) {
            Area area = areaRepository.findById(sectorDTO.getArea().getId())
                    .orElseThrow(() -> new RuntimeException("Area not found"));
            sector.setArea(area);
        }
        return sectorRepository.save(sector);
    }

    public Sector updateSector(Long id, SectorDto sectorDTO) {
        try {
            Sector sector = sectorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sector not found"));
            sector.setSectorName(sectorDTO.getSectorName());

            if (sectorDTO.getHead().getId() != null) {
                Police head = policeRepository.findById(sectorDTO.getHead().getId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                sector.setHead(head);
            }

            if (sectorDTO.getArea().getId() != null) {
                Area area = areaRepository.findById(sectorDTO.getArea().getId())
                        .orElseThrow(() -> new RuntimeException("Area not found"));
                sector.setArea(area);
            }

            return sectorRepository.save(sector);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update sector", e);
        }
    }

    public boolean deleteSector(Long id) {
        try {
            if (sectorRepository.existsById(id)) {
                sectorRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete sector", e);
        }
    }
}
