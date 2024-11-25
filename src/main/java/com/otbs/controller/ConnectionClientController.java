package com.otbs.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ConnectionClientController {
	
	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    // Render the main page (Apply for New Connection)
    @GetMapping("/")
    public String renderPage() {
        return "apply";
    }

    // Get request to render the form for applying for a new connection
    @GetMapping("/apply")
    public String applyForConnectionPage() {
        return "apply";
    }
    
    @GetMapping("/submit")
    public String SubmitApplication() {
        return "SubmitApplication";
    }
    
    
   
}
