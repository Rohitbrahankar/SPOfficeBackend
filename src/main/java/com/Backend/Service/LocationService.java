package com.Backend.Service;

import com.Backend.Dto.LocationDto;
import com.Backend.Entities.Location;
import com.Backend.Entities.Police;
import com.Backend.Entities.Sector;
import com.Backend.Repository.LocationRepository;
import com.Backend.Repository.PoliceRepository;
import com.Backend.Repository.SectorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private PoliceRepository policeRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Location createLocation(LocationDto locationDTO) {

        Location location = new Location();
        location.setLocationName(locationDTO.getLocationName());
        
        location.setEquipments(locationDTO.getEquipments());

        HashSet<Police> polices2 = new HashSet<>();
            for (Long id : locationDTO.getPoliceIds()) {
                Police police = policeRepository.findById((long)id)
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                polices2.add(police);
            }

        location.setPolices(polices2);

        if (locationDTO.getHeadId() != null) {
            Police head = policeRepository.findById(locationDTO.getHeadId())
                    .orElseThrow(() -> new RuntimeException("Police not found"));
            location.setHead(head);
        }

        if (locationDTO.getSectorId() != null) {
            Sector sector = sectorRepository.findById(locationDTO.getSectorId())
                    .orElseThrow(() -> new RuntimeException("Sector not found"));
            location.setSector(sector);
        }

        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, LocationDto locationDTO) {
        try {
            Location existingLocation = locationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Location not found"));

            if (locationDTO.getHeadId() != null) {
                Police head = policeRepository.findById(locationDTO.getHeadId())
                        .orElseThrow(() -> new RuntimeException("Police not found"));
                existingLocation.setHead(head);
            }

            if (locationDTO.getSectorId() != null) {
                Sector sector = sectorRepository.findById(locationDTO.getSectorId())
                        .orElseThrow(() -> new RuntimeException("Sector not found"));
                existingLocation.setSector(sector);
            }

            existingLocation.setLocationName(locationDTO.getLocationName());
            // existingLocation.setPolices(locationDTO.getPolices());
            existingLocation.setEquipments(locationDTO.getEquipments());

            return locationRepository.save(existingLocation);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update location", e);
        }
    }

    public boolean deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
