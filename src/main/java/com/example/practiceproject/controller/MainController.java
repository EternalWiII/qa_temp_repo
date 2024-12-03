package com.example.practiceproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MainController handles navigation for the main pages of the application.
 * It returns view names for home, registration, and login pages.
 */
@Controller
public class MainController {

    /**
     * Handles GET requests to the root URL ("/") and returns the view name for the home page.
     *
     * @return the name of the home view
     */
    @GetMapping("")
    public String home() {
        return "home";
    }

    /**
     * Handles GET requests to the "/registration" URL and returns the view name for the registration page.
     *
     * @return the name of the registration view
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Handles GET requests to the "/login" URL and returns the view name for the login page.
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
