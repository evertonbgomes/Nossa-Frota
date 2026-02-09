package com.progweb.frota.service;

import com.progweb.frota.model.TripRequest;
import com.progweb.frota.model.Vehicle;
import com.progweb.frota.repository.TripRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripRequestService {
    @Autowired
    private TripRequestRepository tripRequestRepository;

    @Autowired
    private VehicleService vehicleService;

    public TripRequest saveTripRequest(TripRequest tripRequest) {
        Vehicle vehicle = tripRequest.getVehicle();
        vehicle.setAvailable(false);
        vehicleService.saveVehicle(vehicle);
        return tripRequestRepository.save(tripRequest);
    }

    public List<TripRequest> getAllTripRequests() {
        return tripRequestRepository.findAll();
    }

    public TripRequest getTripRequestById(Long id) {
        return tripRequestRepository.findById(id).orElse(null);
    }

    public List<TripRequest> getRequestsByStatus(TripRequest.TripStatus status) {
        return tripRequestRepository.findByStatus(status);
    }

    public void deleteTripRequest(Long id) {
        TripRequest tripRequest = tripRequestRepository.findById(id).orElse(null);
        if (tripRequest != null) {
            Vehicle vehicle = tripRequest.getVehicle();
            vehicle.setAvailable(true);
            vehicleService.saveVehicle(vehicle);
            tripRequestRepository.deleteById(id);
        }
    }

    public void updateTripRequest(Long id, TripRequest updatedTripRequest) {
        TripRequest existingTripRequest = tripRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trip request Id:" + id));
        existingTripRequest.setDriver(updatedTripRequest.getDriver());
        existingTripRequest.setVehicle(updatedTripRequest.getVehicle());
        existingTripRequest.setTripStartTime(updatedTripRequest.getTripStartTime());
        existingTripRequest.setTripEndTime(updatedTripRequest.getTripEndTime());
        tripRequestRepository.save(existingTripRequest);
    }

    public void completeTripRequest(Long id) {
        TripRequest tripRequest = tripRequestRepository.findById(id).orElse(null);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.CONCLUIDA);
            Vehicle vehicle = tripRequest.getVehicle();
            vehicle.setAvailable(true);
            vehicleService.saveVehicle(vehicle);
            tripRequestRepository.save(tripRequest);
        }
    }

    public void acceptTripRequest(Long id) {
        TripRequest tripRequest = tripRequestRepository.findById(id).orElse(null);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.ACEITA);
            Vehicle vehicle = tripRequest.getVehicle();
            vehicle.setAvailable(false);
            vehicleService.saveVehicle(vehicle);
            tripRequestRepository.save(tripRequest);
        }
    }

    public void rejectTripRequest(Long id) {
        TripRequest tripRequest = tripRequestRepository.findById(id).orElse(null);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.REJEITADA);
            Vehicle vehicle = tripRequest.getVehicle();
            vehicle.setAvailable(true);
            vehicleService.saveVehicle(vehicle);
            tripRequestRepository.save(tripRequest);
        }
    }
}