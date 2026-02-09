package com.progweb.frota.controller;

import com.progweb.frota.model.TripRequest;
import com.progweb.frota.model.Vehicle;
import com.progweb.frota.service.TripRequestService;
import com.progweb.frota.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TripRequestService tripRequestService;

    @GetMapping
    public String showAdminPage(Model model) {
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles(); 
        List<TripRequest> tripRequests = tripRequestService.getAllTripRequests();
        model.addAttribute("tripRequests", tripRequests);
        model.addAttribute("availableVehicles", availableVehicles);   

        return "admin/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteTripRequest(@PathVariable Long id) {
        tripRequestService.deleteTripRequest(id);
        return "redirect:/admin";
    }

    @GetMapping("/accept/{id}")
    public String acceptTripRequest(@PathVariable Long id) {
        tripRequestService.acceptTripRequest(id);
        return "redirect:/admin";
    }

    @GetMapping("/reject/{id}")
    public String rejectTripRequest(@PathVariable Long id) {
        TripRequest tripRequest = tripRequestService.getTripRequestById(id);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.REJEITADA);
            tripRequestService.rejectTripRequest(id);
        }
        return "redirect:/admin";
    }

    @GetMapping("/complete/{id}")
    public String completeTripRequest(@PathVariable Long id) {
        TripRequest tripRequest = tripRequestService.getTripRequestById(id);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.CONCLUIDA);
            tripRequestService.completeTripRequest(id);
        }
        return "redirect:/admin";
    }
}
