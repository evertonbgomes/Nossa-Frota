package com.progweb.frota.controller;

import com.progweb.frota.model.User;
import com.progweb.frota.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/users/manage";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/manage";
    }
    
    @GetMapping("/manage")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/manage";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users/manage";
    }
}