package com.progweb.frota.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna a página de login
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Retorna a página do dashboard
    }
}