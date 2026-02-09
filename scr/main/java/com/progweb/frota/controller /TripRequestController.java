package com.progweb.frota.controller;

import com.progweb.frota.model.TripRequest;
import com.progweb.frota.model.User;
import com.progweb.frota.model.Vehicle;
import com.progweb.frota.service.TripRequestService;
import com.progweb.frota.service.UserService;
import com.progweb.frota.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/trip-requests")
public class TripRequestController {
    @Autowired
    private TripRequestService tripRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/add")
    public String showAddTripRequestForm(Model model) {
        model.addAttribute("tripRequest", new TripRequest());
        model.addAttribute("drivers", userService.findUsersByRole("MOTORISTA"));
        model.addAttribute("vehicles", vehicleService.getAvailableVehicles());
        return "trip-requests/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditTripRequestForm(@PathVariable("id") Long id, Model model) {
        TripRequest tripRequest = tripRequestService.getTripRequestById(id);
        List<User> drivers = userService.findUsersByRole("MOTORISTA");
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("tripRequest", tripRequest);
        model.addAttribute("drivers", drivers);
        model.addAttribute("vehicles", vehicles);
        return "trip-requests/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTripRequest(@PathVariable("id") Long id, @ModelAttribute("tripRequest") TripRequest tripRequest) {
        tripRequestService.updateTripRequest(id, tripRequest);
        return "redirect:/trip-requests/manage";
    }

    @PostMapping("/add")
    public String addTripRequest(@ModelAttribute TripRequest tripRequest, @RequestParam Long vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        tripRequest.setVehicle(vehicle);
        tripRequest.setRequestTime(LocalDateTime.now());
        tripRequest.setStatus(TripRequest.TripStatus.PENDENTE);
        tripRequestService.saveTripRequest(tripRequest);
        return "redirect:/trip-requests/manage";
    }

    @GetMapping("/manage")
    public String manageTripRequests(Model model) {
        List<TripRequest> tripRequests = tripRequestService.getAllTripRequests();
        model.addAttribute("tripRequests", tripRequests);
        return "trip-requests/manage";
    }

    @GetMapping("/view-trip")
    public String viewTripRequests(Model model) {
        List<TripRequest> tripRequests = tripRequestService.getAllTripRequests();
        model.addAttribute("tripRequests", tripRequests);
        return "trip-requests/view-trip";
    }

    @GetMapping("/delete/{id}")
    public String deleteTripRequest(@PathVariable Long id) {
        tripRequestService.deleteTripRequest(id);
        return "redirect:/trip-requests/manage";
    }
    
    
    @GetMapping("/accept/{id}")
    public String acceptTripRequest(@PathVariable Long id) {
        tripRequestService.acceptTripRequest(id);
        return "redirect:/trip-requests/manage";
    }

    @GetMapping("/reject/{id}")
    public String rejectTripRequest(@PathVariable Long id) {
        TripRequest tripRequest = tripRequestService.getTripRequestById(id);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.REJEITADA);
            tripRequestService.rejectTripRequest(id);
        }
        return "redirect:/trip-requests/manage";
    }

    @GetMapping("/complete/{id}")
    public String completeTripRequest(@PathVariable Long id) {
        TripRequest tripRequest = tripRequestService.getTripRequestById(id);
        if (tripRequest != null) {
            tripRequest.setStatus(TripRequest.TripStatus.CONCLUIDA);
            tripRequestService.completeTripRequest(id);
        }
        return "redirect:/trip-requests/manage";
    }
}
