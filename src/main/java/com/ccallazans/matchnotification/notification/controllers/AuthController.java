package com.ccallazans.matchnotification.notification.controllers;

import com.ccallazans.matchnotification.notification.controllers.dto.CreateNotificationDTO;
import com.ccallazans.matchnotification.notification.controllers.dto.CreateRegisterDTO;
import com.ccallazans.matchnotification.notification.controllers.dto.NotificationResponse;
import com.ccallazans.matchnotification.notification.mappers.NotificationMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public String register(@RequestBody @Valid CreateRegisterDTO createRegisterDTO) {

        return "publico register";
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid CreateRegisterDTO createRegisterDTO) {

        return "publico login";
    }

    @GetMapping("/teste")
    public String teste() {

        return "bloqueado";
    }
}
