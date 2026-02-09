package com.progweb.frota.controller;

import com.progweb.frota.model.Vehicle;
import com.progweb.frota.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/add")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicles/add";
    }

    @PostMapping("/add")
    public String addVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicles/manage";
    }

    @GetMapping("/manage")
    public String manageVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicles/manage";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/vehicles/manage";
    }

    @GetMapping("/edit/{id}")
    public String showEditVehicleForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles/edit";
    }

    @PostMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, @ModelAttribute Vehicle vehicle) {
        vehicle.setId(id);
        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicles/manage";
    }
}
