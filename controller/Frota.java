package com.progweb.frota.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.progweb.frota.model.TripRequest;
import com.progweb.frota.model.User;
import com.progweb.frota.model.Vehicle;
import com.progweb.frota.service.TripRequestService;
import com.progweb.frota.service.UserService;
import com.progweb.frota.service.VehicleService;
import java.time.LocalDateTime;

import java.util.List;

@Controller
@RequestMapping("/")
public class Frota {

    @Autowired
    private TripRequestService tripRequestService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired 
    private UserService userService;


    @GetMapping
    public String showIndexPage(Model model) {
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles(); 
        List<TripRequest> tripRequests = tripRequestService.getAllTripRequests();
        model.addAttribute("tripRequests", tripRequests);
        model.addAttribute("availableVehicles", availableVehicles);   

        return "index";
    }
    @GetMapping("/index")
    public String viewTripRequests(Model model) {
        List<TripRequest> tripRequests = tripRequestService.getAllTripRequests();
        model.addAttribute("tripRequests", tripRequests);
        return "trip-requests/";
    }

    @GetMapping("index/user-delete/{id}")
    public String userdeleteTripRequest(@PathVariable Long id) {
        tripRequestService.deleteTripRequest(id);
        return "redirect:/";
    }

        @GetMapping("index/trip-add")
    public String showAddTripRequestForm(Model model) {
        model.addAttribute("tripRequest", new TripRequest());
        model.addAttribute("drivers", userService.findUsersByRole("MOTORISTA"));
        model.addAttribute("vehicles", vehicleService.getAvailableVehicles());
        return "index/trip-add";
    }

    @GetMapping("index/trip-edit/{id}")
    public String showEditTripRequestForm(@PathVariable("id") Long id, Model model) {
        TripRequest tripRequest = tripRequestService.getTripRequestById(id);
        List<User> drivers = userService.findUsersByRole("MOTORISTA");
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        model.addAttribute("tripRequest", tripRequest);
        model.addAttribute("drivers", drivers);
        model.addAttribute("vehicles", vehicles);
        return "index/trip-edit";
    }

    @PostMapping("index/trip-edit/{id}")
    public String updateTripRequest(@PathVariable("id") Long id, @ModelAttribute("tripRequest") TripRequest tripRequest) {
        tripRequestService.updateTripRequest(id, tripRequest);
        return "redirect:/";
    }

    @PostMapping("index/trip-add")
    public String addTripRequest(@ModelAttribute TripRequest tripRequest, @RequestParam Long vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        tripRequest.setVehicle(vehicle);
        tripRequest.setRequestTime(LocalDateTime.now());
        tripRequest.setStatus(TripRequest.TripStatus.PENDENTE);
        tripRequestService.saveTripRequest(tripRequest);
        return "redirect:/";
    }

}
