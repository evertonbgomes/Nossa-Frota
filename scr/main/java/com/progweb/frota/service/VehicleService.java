package com.progweb.frota.service;

import com.progweb.frota.model.Vehicle;
import com.progweb.frota.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicle.getAvailable() == null) {
            vehicle.setAvailable(true); // or true, depending on your business logic
        }
        return vehicleRepository.save(vehicle);

    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findByAvailable(true);
    }

    
    
}
