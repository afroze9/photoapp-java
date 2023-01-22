package com.afroze.photoapp.api.users.ui.controllers;


import com.afroze.photoapp.api.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final Environment env;

    @Autowired
    public DemoController(Environment env) {
        this.env = env;
    }

    @GetMapping("/status")
    public String status() {
        return "Demo working on port: " + env.getProperty("local.server.port");
    }
}
